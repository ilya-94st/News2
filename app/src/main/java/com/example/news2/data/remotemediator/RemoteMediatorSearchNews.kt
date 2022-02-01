package com.example.news2.data.remotemediator

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bumptech.glide.load.HttpException
import com.example.news2.data.api.ApiNews
import com.example.news2.data.db.ArticleDatabase
import com.example.news2.data.downloadfile.CreateFileArticle
import com.example.news2.data.toEntitySearchNews
import com.example.news2.domain.model.entity.RemoteKeysForSearchNews
import com.example.news2.domain.model.entity.SearchNewsEntity
import com.example.news2.util.Constants
import java.io.*

@ExperimentalPagingApi
class RemoteMediatorSearchNews(
    private val api: ApiNews,
    private val query: String,
    private val db: ArticleDatabase,
    private val app: Context
) : RemoteMediator<Int, SearchNewsEntity>() {
    private var createFileArticle: CreateFileArticle? = null

    @SuppressLint("LongLogTag")
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchNewsEntity>
    ): MediatorResult {
        createFileArticle = CreateFileArticle(api, app)
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: Constants.STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        try {
            val apiResponse = api.getSearchNews(query = query, page, state.config.pageSize)

            val articles = apiResponse.body()!!.articles

            val entities = mutableListOf<SearchNewsEntity>()

            articles.forEach {
                val entity = it.toEntitySearchNews()
                createFileArticle!!.downloadArticle(entity)
                entities.add(entity)
            }

            val endOfPaginationReached = articles.isEmpty()
            db.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    val oldList = db.getDaoSearchNews().readOldListSearchNews()
                    oldList.forEach {
                        if(it.path.isNotEmpty()) {
                            try {
                                File(it.path).delete()
                            } catch (e: Exception){
                                Log.e("RemoteMediatorSearchNews", "$e")
                            }
                        }
                            db.remoteKeysDaoSearch().clearRemoteKeys()
                            db.getDaoSearchNews().deleteId(it.path)
                    }
                }
                val prevKey = if (page == Constants.STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = articles.map {
                    RemoteKeysForSearchNews(articleId = it.id.toLong(), prevKey = prevKey, nextKey = nextKey)
                }
                db.remoteKeysDaoSearch().insertAll(keys)
                entities.forEach {
                    db.getDaoSearchNews().insertSearchNews(it)
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, SearchNewsEntity>): RemoteKeysForSearchNews? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { articleId ->
                // Get the remote keys of the last item retrieved
                db.remoteKeysDaoSearch().remoteKeysRepoId(articleId.id.toLong())
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, SearchNewsEntity>): RemoteKeysForSearchNews? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { articleId ->
                // Get the remote keys of the first items retrieved
                db.remoteKeysDaoSearch().remoteKeysRepoId(articleId.id.toLong())
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, SearchNewsEntity>
    ): RemoteKeysForSearchNews? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { articleId ->
                db.remoteKeysDaoSearch().remoteKeysRepoId(articleId.toLong())
            }
        }
    }
}