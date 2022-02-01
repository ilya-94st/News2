package com.example.news2.domain.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.news2.domain.model.responce.Source
import java.io.Serializable

@Entity(tableName = "search_news")
data class SearchNewsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "publishedAt") val publishedAt: String?,
    @ColumnInfo(name = "source") val source: Source?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "urlToImage") val urlToImage: String?,
    var path: String
): Serializable