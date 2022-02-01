package com.example.news2.presentation.ui.vievmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.news2.domain.use_case.get_breaking_news.GetBreakingNews
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelsBreakingNews @Inject constructor(private val getBreakingNews: GetBreakingNews): ViewModel(){

    fun getBreakingNews(countryCode: String, category:String) = getBreakingNews.invoke(countryCode, category).cachedIn(viewModelScope)
}