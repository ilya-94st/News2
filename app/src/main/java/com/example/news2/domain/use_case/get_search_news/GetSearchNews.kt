package com.example.news2.domain.use_case.get_search_news

import androidx.paging.PagingData
import com.example.news2.domain.model.entity.SearchNewsEntity
import com.example.news2.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetSearchNews @Inject constructor(private val articleRepository: ArticleRepository){

    operator fun invoke(query: String):Flow<PagingData<SearchNewsEntity>> {
        return articleRepository.getSearchNews(query)
    }

}