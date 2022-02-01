package com.example.news2.domain.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_search_news")
data class RemoteKeysForSearchNews(
    @PrimaryKey
    val articleId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)