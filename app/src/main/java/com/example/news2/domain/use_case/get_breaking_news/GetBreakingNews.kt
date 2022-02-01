package com.example.news2.domain.use_case.get_breaking_news

import androidx.paging.PagingData
import com.example.news2.domain.model.responce.Article
import com.example.news2.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBreakingNews @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    operator fun invoke(country: String, category: String): Flow<PagingData<Article>> {
        return articleRepository.getBreakingNews(country = country, category = category)
    }
}



