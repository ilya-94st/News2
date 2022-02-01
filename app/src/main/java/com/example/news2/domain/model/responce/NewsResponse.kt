package com.example.news2.domain.model.responce

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)