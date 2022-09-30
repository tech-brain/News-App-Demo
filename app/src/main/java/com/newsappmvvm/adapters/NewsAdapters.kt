package com.newsappmvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newsappmvvm.R
import com.newsappmvvm.databinding.ItemNewsBinding
import com.newsappmvvm.model.NewsData
import com.newsappmvvm.utils.DateUtils.getNewsTime
import java.lang.ref.SoftReference

class NewsAdapters(private val newsList: ArrayList<NewsData.Article>, private val adapterClick: AdapterClick) :
    RecyclerView.Adapter<NewsAdapters.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class ViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NewsData.Article) {
            binding.tvTitle.text = data.title
            binding.tvDescription.text = data.description
            binding.tvSourceName.text = data.source?.name
            binding.tvDate.text = getNewsTime(data.publishedAt)
            Glide.with(binding.root).load(data.urlToImage).placeholder(R.drawable.placeholder).into(binding.ivNewsBanner)

            binding.root.setOnClickListener {
                data.url?.let { it1 -> adapterClick.onViewNews(it1) }
            }
        }
    }

    interface AdapterClick{
        fun onViewNews(url : String)
    }
}