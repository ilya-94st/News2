package com.example.news2.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.news2.R
import com.example.news2.presentation.base.BaseFragment
import com.example.news2.databinding.FragmentSearchNewsBinding
import com.example.news2.presentation.adapters.ArticleLoadAdapter
import com.example.news2.presentation.adapters.SearchNewsAdapter
import com.example.news2.presentation.ui.prefs
import com.example.news2.presentation.ui.vievmodels.ViewModelsSearchNews
import com.example.news2.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchNewsFragment : BaseFragment<FragmentSearchNewsBinding>(){
    private val viewModelNews: ViewModelsSearchNews by viewModels()
    private lateinit var articleAdapter: SearchNewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                val query = prefs.saveQuery
                viewModelNews.getSearchNews(query).collectLatest {
                    articleAdapter.submitData(it)
            }
        }

        searchNews()
        swipeRefresh()
        articleAdapter.setOnItemClickListner {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_searchNewsFragment_to_articleFragment, bundle)
        }
    }

    private fun searchNews(){
        var job: Job? = null
        binding.editSearch.addTextChangedListener { editable ->
            job?.cancel()
           job = CoroutineScope(Dispatchers.Default).launch {
               delay(Constants.TIME_SEARCH)
              editable.let {
                  if (editable.toString().isNotEmpty()){
                      val search = editable.toString()
                      viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                          prefs.saveQuery = search
                          viewModelNews.getSearchNews(search).collectLatest {
                              articleAdapter.submitData(it)
                          }
                      }
                      binding.editSearch.hideKeyboard()
                  }
              }
           }
        }
    }

    private fun initAdapter() {
        articleAdapter = SearchNewsAdapter()
        binding.rvArticle.adapter = articleAdapter.withLoadStateFooter(
            footer = ArticleLoadAdapter()
        )
        articleAdapter.addLoadStateListener { state: CombinedLoadStates ->
            val refreshState = state.refresh
            binding.rvArticle.isVisible = refreshState != LoadState.Loading
            binding.paginationProgressBar.isVisible = refreshState == LoadState.Loading
            if (refreshState is LoadState.Error) {
                snackBar("${refreshState.error}")
            }
        }
    }

    private fun swipeRefresh() {
        binding.swipe.setOnRefreshListener {
            articleAdapter.refresh()
            binding.swipe.isRefreshing = false
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSearchNewsBinding.inflate(inflater, container ,false)
}