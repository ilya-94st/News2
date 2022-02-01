package com.example.news2.presentation.ui.vievmodels

import androidx.lifecycle.ViewModel
import com.example.news2.domain.use_case.get_search_news.GetSearchNews
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class  ViewModelsSearchNews @Inject constructor(private val getSearchNews: GetSearchNews): ViewModel() {

    fun getSearchNews(query: String) = getSearchNews.invoke(query)
}