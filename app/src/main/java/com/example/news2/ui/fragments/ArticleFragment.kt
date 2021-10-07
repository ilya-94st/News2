package com.example.news2.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.news2.R
import com.example.news2.base.BaseFragment
import com.example.news2.databinding.FragmentArticleBinding
import com.example.news2.ui.MainActivity
import com.example.news2.ui.ViewModelNews


class ArticleFragment :BaseFragment<FragmentArticleBinding>(){
    val args: ArticleFragmentArgs by navArgs()
    lateinit var viewModelNews: ViewModelNews
    override fun getBinding() = R.layout.fragment_article
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inVisibleMenu(R.id.botomNavigation)
        val article = args.article
        viewModelNews = (activity as MainActivity).viewModelNews
        binding.floatingActionButton.setOnClickListener {
           try {
               viewModelNews.insert(article)
               snackBar("save article")
           }catch (e: Exception){
               toast("${e}")
           }
        }
        webView(article.url)
    }
    fun webView(urlArticle: String) {
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(urlArticle)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        visibleMenu(R.id.botomNavigation)
    }
}