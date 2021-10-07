package com.example.news2.api

import com.example.news2.model.NewsResponse
import com.example.news2.util.Const.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface apiNews {

    @GET("v2/top-headlines")
    suspend fun getBreackingNews(
        @Query("country")
        country: String = "ru",
        @Query("category")
        category:String = "health",
        @Query("page")
        page: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getSearchNews(
        @Query("q")
        query: String,
        @Query("page")
        page: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}