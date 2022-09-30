package com.newsappmvvm.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.newsappmvvm.R
import com.newsappmvvm.adapters.CategoryAdapter
import com.newsappmvvm.adapters.NewsAdapters
import com.newsappmvvm.api.Response
import com.newsappmvvm.applications.MyApplication
import com.newsappmvvm.databinding.ActivityMainBinding
import com.newsappmvvm.model.CategoryModel
import com.newsappmvvm.model.NewsData
import com.newsappmvvm.viewModel.NewsViewModel
import com.newsappmvvm.viewModel.NewsViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), NewsAdapters.AdapterClick,
    CategoryAdapter.CategoryAdapterClick {
    val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NewsViewModel
    private lateinit var adapters: NewsAdapters
    lateinit var catAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = (application as MyApplication).newsRepository
        viewModel =
            ViewModelProvider(this, NewsViewModelFactory(repository))[NewsViewModel::class.java]
        observeResponse()

    }

    private fun observeResponse() {
        viewModel.news.observe(this) {
            when (it) {
                is Response.Loading -> {}
                is Response.Success -> {
                    Log.e(TAG, "response ${it.data?.articles}")
                    adapters = NewsAdapters(it.data?.articles as ArrayList<NewsData.Article>, this)
                    binding.rvNews.adapter = adapters

                    catAdapter = CategoryAdapter(viewModel.categoryList, this)
                    binding.rvCategories.adapter = catAdapter
                }
                is Response.Error -> {
                    Log.e(TAG, "error ${R.string.error_message}")
                }
            }
        }

    }

    override fun onViewNews(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    override fun onChangeCat(data: CategoryModel, position: Int) {
        if(data.selected != true){
            GlobalScope.launch(Dispatchers.IO) {
                if(position == 0){
                    viewModel.callApi("")
                }else{
                    viewModel.callApi(data.category?:"")
                }
            }
        }
        val model = data
        for(i in 0 until viewModel.categoryList.size){
            viewModel.categoryList[i].selected = position == i
        }

        viewModel.categoryList[position] = model
        catAdapter.notifyItemChanged(position)
    }
}
