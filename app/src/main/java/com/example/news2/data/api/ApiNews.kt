package com.example.news2.data.api

import com.example.news2.domain.model.responce.NewsResponse
import com.example.news2.util.Constants.API_KEY
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ApiNews {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        country: String,
        @Query("category")
        category:String,
        @Query("pageSize")
        pageSize: Int,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getSearchNews(
        @Query("q")
        query: String,
        @Query("page")
        page: Int,
        @Query("pageSize")
        pageSize: Int,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @Streaming
    @GET
    fun downloadFile(@Url fileUrl: String): Call<ResponseBody>
}