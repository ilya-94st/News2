package com.example.news2.data

import com.example.news2.domain.model.entity.SearchNewsEntity
import com.example.news2.domain.model.responce.Article



fun Article.toEntitySearchNews(): SearchNewsEntity {
    return SearchNewsEntity(
        id, content, description, publishedAt, source, title, url, urlToImage,
        path = ""
    )
}




