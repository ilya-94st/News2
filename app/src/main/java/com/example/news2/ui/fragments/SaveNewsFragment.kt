package com.example.news2.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.news2.R
import com.example.news2.adapters.ArticleAdapter
import com.example.news2.base.BaseFragment
import com.example.news2.databinding.FragmentSaveNewsBinding
import com.example.news2.ui.MainActivity
import com.example.news2.ui.ViewModelNews
import com.google.android.material.snackbar.Snackbar


class SaveNewsFragment : BaseFragment<FragmentSaveNewsBinding>() {
    lateinit var articleAdapter: ArticleAdapter
    lateinit var viewModelNews: ViewModelNews
    override fun getBinding() = R.layout.fragment_save_news
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
  viewModelNews = (activity as MainActivity).viewModelNews
initAdapter()
        articleAdapter.setOnItemClickListner {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_saveNewsFragment_to_articleFragment, bundle)
        }
        viewModelNews.readArticle().observe(viewLifecycleOwner, { article->
            articleAdapter.submitList(article)
        })
 deleteArticleSwaip()
    }
    private fun deleteArticleSwaip(){
        val itemTouchHelperCallBack = object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN
            , ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }
            
            @SuppressLint("ShowToast")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = articleAdapter.differ.currentList[position]
                viewModelNews.deleteArticle(article)
                view?.let {
                    Snackbar.make(it, "aricle successfull delete", Snackbar.LENGTH_LONG).apply {
                        setAction("Undo"){
                            viewModelNews.insert(article)
                        }
                        show()
                    }
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvArticle)
        }
    }
    private fun initAdapter(){
        articleAdapter = ArticleAdapter()
    binding.rvArticle.adapter = articleAdapter
    }
}