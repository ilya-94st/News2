package com.example.news2.repository

import com.example.news2.api.RetrofitInstanse
import com.example.news2.db.ArticleDatabase
import com.example.news2.model.Article
import kotlinx.coroutines.flow.Flow


class RepositoryNews(var db: ArticleDatabase) {
    suspend fun getBreackingNews(countryCode: String,category:String, page: Int) = RetrofitInstanse.api.getBreackingNews(countryCode, category,page)
    suspend fun getSearchNews(query: String, page: Int) = RetrofitInstanse.api.getSearchNews(query, page)

    suspend fun insertArticle(article: Article) = db.getArticleDao().insertArticle(article)

    suspend fun deleteArticle(article: Article) = db.getArticleDao().delete(article)

    fun readArticles() = db.getArticleDao().readArticles()
}