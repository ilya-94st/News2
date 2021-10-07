package com.example.news2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news2.repository.RepositoryNews

@Suppress("UNCHECKED_CAST")
class NewsProviderFactory(val news: RepositoryNews): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelNews(news) as T
    }
}