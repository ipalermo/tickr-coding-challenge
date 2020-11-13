package com.tickr.challenge.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tickr.challenge.NewsFragment
import com.tickr.challenge.adapters.ArticleAdapter.ArticleViewHolder
import com.tickr.challenge.data.GuardianArticle
import com.tickr.challenge.databinding.ListItemArticleBinding

/**
 * Adapter for the [RecyclerView] in [NewsFragment].
 */

class ArticleAdapter : PagingDataAdapter<GuardianArticle, ArticleViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ListItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null) {
            holder.bind(article)
        }
    }

    class ArticleViewHolder(
        private val binding: ListItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                binding.article?.let { article ->
                    val uri = Uri.parse(article.webUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    view.context.startActivity(intent)
                }
            }
        }

        fun bind(item: GuardianArticle) {
            binding.apply {
                article = item
                executePendingBindings()
            }
        }
    }
}

private class ArticleDiffCallback : DiffUtil.ItemCallback<GuardianArticle>() {
    override fun areItemsTheSame(oldItem: GuardianArticle, newItem: GuardianArticle): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GuardianArticle, newItem: GuardianArticle): Boolean {
        return oldItem == newItem
    }
}
