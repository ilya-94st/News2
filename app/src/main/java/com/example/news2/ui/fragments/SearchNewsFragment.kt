package com.example.news2.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.news2.R
import com.example.news2.adapters.ArticleAdapter
import com.example.news2.base.BaseFragment
import com.example.news2.databinding.FragmentSearchNewsBinding
import com.example.news2.ui.MainActivity
import com.example.news2.ui.ViewModelNews
import com.example.news2.util.Const
import com.example.news2.util.Resourse
import kotlinx.coroutines.*

class SearchNewsFragment : BaseFragment<FragmentSearchNewsBinding>(){
    lateinit var viewModelNews: ViewModelNews
    lateinit var newsAdapter: ArticleAdapter
    override fun getBinding() = R.layout.fragment_search_news
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelNews = (activity as MainActivity).viewModelNews
        initAdapter()
        searchNews()
        newsAdapter.setOnItemClickListner {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_searchNewsFragment_to_articleFragment, bundle)
        }
        viewModelNews.searchNews().observe(viewLifecycleOwner, Observer {
                response ->
            when(response){
                is Resourse.Success ->{
                    hideProgressBar()
                    response.data?.let {
                            newsResponse ->
                        newsAdapter.submitList(newsResponse.articles)
                    }
                }
                is Resourse.Error ->{
                    hideProgressBar()
                    response.data?.let {
                            message->
                        Toast.makeText(activity, "Error${message}", Toast.LENGTH_SHORT).show()
                    }

                }
                is Resourse.Loading ->{
                    showProgressBar()
                }
            }
        })
    }
    private fun searchNews(){
        var job: Job? = null
        binding.editSearch.addTextChangedListener { editable ->
            job?.cancel()
           job = CoroutineScope(Dispatchers.Default).launch {
               delay(Const.TIME_SEARCH)
              editable.let {
                  if (editable.toString().isNotEmpty()){
                      viewModelNews.getSearchNews(editable.toString())
                      binding.editSearch.hideKeyboard()
                  }
              }
           }
        }
    }
    private fun initAdapter() {
        newsAdapter = ArticleAdapter()
        binding.rvNews.adapter = newsAdapter
    }
    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }
}