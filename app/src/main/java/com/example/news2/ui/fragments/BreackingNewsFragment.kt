package com.example.news2.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.news2.R
import com.example.news2.adapters.ArticleAdapter
import com.example.news2.base.BaseFragment
import com.example.news2.databinding.FragmentBreackingNewsBinding
import com.example.news2.ui.MainActivity
import com.example.news2.ui.ViewModelNews
import com.example.news2.util.Resourse
import kotlinx.coroutines.flow.collect


class BreackingNewsFragment : BaseFragment<FragmentBreackingNewsBinding>(){
    lateinit var viewModelNews: ViewModelNews
    lateinit var articleAdapter: ArticleAdapter
    override fun getBinding() = R.layout.fragment_breacking_news
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelNews = (activity as MainActivity).viewModelNews
        initAdapter()
        articleAdapter.setOnItemClickListner {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_breackingNewsFragment_to_articleFragment, bundle)
        }
        lifecycleScope.launchWhenCreated {
            viewModelNews.breakingNews().collect {
                    response ->
                when(response){
                    is Resourse.Success ->{
                        hideProgressBar()
                        response.data?.let {
                                newsResponse ->
                            articleAdapter.submitList(newsResponse.articles)
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
            }
        }

    }
    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }
    private fun initAdapter() {
        articleAdapter = ArticleAdapter()
        binding.rvArticle.adapter =articleAdapter
    }
}