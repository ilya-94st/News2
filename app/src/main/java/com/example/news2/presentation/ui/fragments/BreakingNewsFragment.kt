package com.example.news2.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.news2.R
import com.example.news2.presentation.base.BaseFragment
import com.example.news2.databinding.FragmentBreackingNewsBinding
import com.example.news2.data.shared.SharedPref
import com.example.news2.presentation.adapters.ArticleLoadAdapter
import com.example.news2.presentation.adapters.BreakingNewsAdapter
import com.example.news2.presentation.ui.vievmodels.ViewModelsBreakingNews
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class BreakingNewsFragment : BaseFragment<FragmentBreackingNewsBinding>(){
    private val viewModelNews: ViewModelsBreakingNews by viewModels()
    private lateinit var sharedPref: SharedPref
    private lateinit var articleAdapter: BreakingNewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        visibleMenu(R.id.botomNavigation)
        sharedPref = SharedPref(requireContext())
        initAdapter()
        swipeRefresh()
        lifecycleScope.launchWhenCreated {
            viewModelNews.getBreakingNews("ru", "business").collectLatest {
                    articleAdapter.submitData(it)
            }
        }

        articleAdapter.setOnItemClickListner {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_breackingNewsFragment_to_articleFragment, bundle)
        }

        binding.logout.setOnClickListener {
            exit()
        }
    }

    private fun initAdapter() {
        articleAdapter = BreakingNewsAdapter()
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

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentBreackingNewsBinding.inflate(inflater, container, false)

    private fun swipeRefresh() {
        binding.swipe.setOnRefreshListener {
            articleAdapter.refresh()
            binding.swipe.isRefreshing = false
        }
    }

    private fun exit() {
        val editor = sharedPref.preferences.edit()
        editor.clear()
        editor.apply()
        findNavController().navigate(R.id.action_breackingNewsFragment_to_registrationFragment)
    }
}