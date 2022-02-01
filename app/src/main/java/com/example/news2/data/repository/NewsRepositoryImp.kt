package com.example.news2.data.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.news2.data.api.ApiNews
import com.example.news2.data.db.ArticleDatabase
import com.example.news2.data.pagingsourse.ArticlePagingSource
import com.example.news2.data.remotemediator.RemoteMediatorSearchNews
import com.example.news2.domain.model.entity.SearchNewsEntity
import com.example.news2.domain.model.responce.Article
import com.example.news2.domain.repository.ArticleRepository
import com.example.news2.util.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NewsRepositoryImp @Inject constructor(private var db: ArticleDatabase, private val apiNews: ApiNews, private val app: Context): ArticleRepository {
    private val pagingSourceFactoryForSearchNews =  { db.getDaoSearchNews().readSearchNews()}
    override fun getBreakingNews(country: String, category: String): Flow<PagingData<Article>> {
           return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ArticlePagingSource(apiNews, country = country, category = category) }
        ).flow
    }

    @ExperimentalPagingApi
    override fun getSearchNews(query: String): Flow<PagingData<SearchNewsEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = RemoteMediatorSearchNews(
                apiNews,
                query,
                db,
                app
            ),
            pagingSourceFactory = pagingSourceFactoryForSearchNews
        ).flow
    }
}