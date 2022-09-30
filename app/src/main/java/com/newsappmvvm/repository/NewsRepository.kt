package com.newsappmvvm.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.newsappmvvm.R
import com.newsappmvvm.api.API_KEY
import com.newsappmvvm.api.NewsService
import com.newsappmvvm.api.Response
import com.newsappmvvm.model.NewsData
import com.newsappmvvm.utils.NetworkUtils

class NewsRepository(private val newsService: NewsService, private val context: Context) {
    private val TAG = "NewsRepository---"
    private val newsLiveData = MutableLiveData<Response<NewsData>>()

    val news: LiveData<Response<NewsData>> get() = newsLiveData

    suspend fun getNews(country: String, category: String) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            try {
                val result = newsService.getNews(country, category, API_KEY)
                if (result.isSuccessful) {
                    if (result.body() != null) {
                        if (result.body()?.status == "ok") {
                            newsLiveData.postValue(Response.Success(result.body()))
                        } else {
                            newsLiveData.postValue(Response.Error(result.errorBody().toString()))
                            Log.e(TAG, "1 " + result.errorBody().toString())
                        }
                    }
                } else {
                    newsLiveData.postValue(Response.Error(context.getString(R.string.error_message)))
                    Log.e(TAG, "2 " + result.body())
                }
            } catch (e: Exception) {
                newsLiveData.postValue(Response.Error(e.message.toString()))
                Log.e(TAG, "3 " + e.message.toString())
            }
        } else {

        }
    }
}