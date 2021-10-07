package com.example.news2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news2.model.Article
import com.example.news2.model.NewsResponse
import com.example.news2.repository.RepositoryNews
import com.example.news2.util.Resourse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModelNews(var newsRepository: RepositoryNews): ViewModel() {
   private val _breakingNews: MutableStateFlow<Resourse<NewsResponse>> = MutableStateFlow(Resourse.Loading())
    fun breakingNews(): StateFlow<Resourse<NewsResponse>> {
        return _breakingNews
    }
    private val pageBreakingNews = 1

    private val _searchNews: MutableLiveData<Resourse<NewsResponse>> = MutableLiveData()
    fun searchNews(): LiveData<Resourse<NewsResponse>> {
        return _searchNews
    }
    private val searchPage = 1

    init {
      getBreakingNews("ru","business")
    }
     fun getBreakingNews(countryCode: String, category:String) = viewModelScope.launch {
     _breakingNews.value = Resourse.Loading()
        val response = newsRepository.getBreackingNews(countryCode,category, pageBreakingNews)
     _breakingNews.value = handleBreakingNews(response)
    }
    fun getSearchNews(query: String) = viewModelScope.launch {
        _searchNews.postValue(Resourse.Loading())
        val response = newsRepository.getSearchNews(query, searchPage)
        _searchNews.postValue(handleSearchNews(response))
    }
    private fun handleBreakingNews(response: Response<NewsResponse>) : Resourse<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                    resultResponse->
                return Resourse.Success(resultResponse)
            }
        }
        return  Resourse.Error(response.message())
    }
    private fun handleSearchNews(response: Response<NewsResponse>) : Resourse<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                    resultResponse->
                return Resourse.Success(resultResponse)
            }
        }
        return  Resourse.Error(response.message())
    }

    fun insert(article: Article) = viewModelScope.launch {
        newsRepository.insertArticle(article)
    }


    fun readArticle() = newsRepository.readArticles()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}