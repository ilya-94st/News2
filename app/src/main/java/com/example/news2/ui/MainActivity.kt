package com.example.news2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.news2.R
import com.example.news2.databinding.ActivityMainBinding
import com.example.news2.db.ArticleDatabase
import com.example.news2.repository.RepositoryNews

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var viewModelNews: ViewModelNews
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val navHostFragment =supportFragmentManager.findFragmentById((R.id.fragmentContainerView)) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.botomNavigation.setupWithNavController(navController)
        val newsRepositoryNews = RepositoryNews(ArticleDatabase(this))
        val viewModelFactory = NewsProviderFactory(newsRepositoryNews)
        viewModelNews = ViewModelProvider(this, viewModelFactory).get(ViewModelNews::class.java)
    }
}