package com.dicoding.asclepius.article

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ItemArticleBinding
import com.dicoding.asclepius.response.ArticlesItem
import com.squareup.picasso.Picasso

class ArticleAdapter : ListAdapter<ArticlesItem, ArticleAdapter.ArticleViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticlesItem) {
            binding.tvTitle.text = article.title
            binding.tvDescription.text = article.description

            val Urlimage = article.urlToImage?.toString()
            if (!Urlimage.isNullOrEmpty()) {
                Picasso.get().load(Urlimage).into(binding.imgArticle)
            } else {
                binding.imgArticle.setImageResource(R.drawable.ic_place_holder)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailArticle::class.java)
                intent.putExtra("title", article.title)
                intent.putExtra("description", article.description)
                intent.putExtra("author", article.author.toString())
                intent.putExtra("url", article.url)
                intent.putExtra("imageUrl", article.urlToImage.toString())
                itemView.context.startActivity(intent)
            }
        }
    }
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem> () {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}