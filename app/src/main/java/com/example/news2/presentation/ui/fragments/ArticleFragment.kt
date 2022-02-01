package com.example.news2.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.news2.R
import com.example.news2.presentation.base.BaseFragment
import com.example.news2.databinding.FragmentArticleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment :BaseFragment<FragmentArticleBinding>(){
    private val args: ArticleFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inVisibleMenu(R.id.botomNavigation)
        val article = args.article

        webView(article.url)
    }
    private fun webView(urlArticle: String) {
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(urlArticle)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        visibleMenu(R.id.botomNavigation)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentArticleBinding.inflate(inflater, container, false)
}