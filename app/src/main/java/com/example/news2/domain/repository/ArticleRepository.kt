package com.example.news2.domain.repository

import androidx.paging.PagingData
import com.example.news2.domain.model.entity.SearchNewsEntity
import com.example.news2.domain.model.responce.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    fun getBreakingNews(country: String, category: String): Flow<PagingData<Article>>

    fun getSearchNews(query: String): Flow<PagingData<SearchNewsEntity>>

}