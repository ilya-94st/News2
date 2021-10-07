package com.example.news2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.news2.databinding.ItemsArticleAdapterBinding
import com.example.news2.model.Article

class ArticleAdapter() :  RecyclerView.Adapter<ArticleAdapter.RunViewHolder>() {

    inner class RunViewHolder(var binding: ItemsArticleAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    val diffCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Article>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        val binding = ItemsArticleAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RunViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        holder.binding.article = run
        holder.itemView.setOnClickListener {
            onItemClickListner.let {
                it(run)
            }
        }
    }
    private var onItemClickListner: (Article)->Unit = {article: Article -> Unit }

    fun setOnItemClickListner(listner: (Article) ->Unit) {
        onItemClickListner = listner
    }
}



