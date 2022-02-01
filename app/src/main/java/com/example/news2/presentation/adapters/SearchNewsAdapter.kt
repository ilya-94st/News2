package com.example.news2.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news2.databinding.ItemsArticleAdapterBinding
import com.example.news2.domain.model.entity.SearchNewsEntity

class SearchNewsAdapter : PagingDataAdapter<SearchNewsEntity, SearchNewsAdapter.RunViewHolder>(
    ARTICLE_COMPARATOR) {

    inner class RunViewHolder(var binding: ItemsArticleAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    companion object{
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<SearchNewsEntity>(){
            override fun areItemsTheSame(oldItem: SearchNewsEntity, newItem: SearchNewsEntity) =
                oldItem.id == newItem.id
            override fun areContentsTheSame(
                oldItem: SearchNewsEntity,
                newItem: SearchNewsEntity
            ) = oldItem.id == newItem.id

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        val binding = ItemsArticleAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RunViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.itemView.apply {

            if (currentItem != null) {
                if (currentItem.path.isNotEmpty()) {
                    Glide.with(this).load(currentItem.path).into(holder.binding.ivArticleImage)
                } else {
                    Glide.with(this).load(currentItem.urlToImage).into(holder.binding.ivArticleImage)
                }
            }
        }
        if (currentItem != null) {
            holder.binding.tvDescription.text = currentItem.description
            holder.binding.tvPublishedAt.text = currentItem.publishedAt
            holder.binding.tvSource.text = currentItem.source?.name
            holder.binding.tvTitle.text = currentItem.title
        }
        holder.itemView.setOnClickListener {
            onItemClickListner.let {
                if (currentItem != null) {
                    it(currentItem)
                }
            }
        }
    }
    private var onItemClickListner: (SearchNewsEntity)->Unit = { article: SearchNewsEntity -> Unit }

    fun setOnItemClickListner(listner: (SearchNewsEntity) ->Unit) {
        onItemClickListner = listner
    }
}
