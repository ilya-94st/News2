package com.example.news2.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.news2.model.Article

@Dao
interface DaoArticle {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article):Long

    @Query("select  * from news")
    fun readArticles(): LiveData<List<Article>>

    @Delete
    suspend fun delete(article: Article)
}