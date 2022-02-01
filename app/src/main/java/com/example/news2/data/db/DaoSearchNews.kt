package com.example.news2.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.news2.domain.model.entity.SearchNewsEntity

@Dao
interface DaoSearchNews {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchNews(article: SearchNewsEntity)

    @Query("delete from search_news")
    suspend fun deleteAllSearchNews()

    @Query("select * from search_news order by id desc")
    fun readSearchNews(): PagingSource<Int, SearchNewsEntity>

    @Query("select * from search_news")
    fun readOldListSearchNews(): List<SearchNewsEntity>

    @Query("delete from search_news where path =:path")
    fun deleteId(path: String)
}