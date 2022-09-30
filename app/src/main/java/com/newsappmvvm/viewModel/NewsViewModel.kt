package com.newsappmvvm.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsappmvvm.api.Response
import com.newsappmvvm.model.CategoryModel
import com.newsappmvvm.model.NewsData
import com.newsappmvvm.repository.NewsRepository
import com.newsappmvvm.utils.CategoryTypes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
     private val newsCategory by lazy { ObservableField(CategoryTypes.ALL) }
        val categoryList : ArrayList<CategoryModel> = ArrayList()
    init {

        categoryList.add(CategoryModel("All", true))
        categoryList.add(CategoryModel("business", false))
        categoryList.add(CategoryModel("entertainment", false))
        categoryList.add(CategoryModel("general", false))
        categoryList.add(CategoryModel("health", false))
        categoryList.add(CategoryModel("science", false))
        categoryList.add(CategoryModel("sports", false))
        categoryList.add(CategoryModel("technology", false))

        viewModelScope.launch(Dispatchers.IO) {
            callApi(newsCategory.get()?.value?:"")
        }
    }
        val news: LiveData<Response<NewsData>> get() =newsRepository.news

    suspend fun callApi(category : String){
        newsRepository.getNews("in", category)
    }
}