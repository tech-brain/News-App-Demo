package com.newsappmvvm.applications

import android.app.Application
import com.newsappmvvm.api.NewsService
import com.newsappmvvm.api.RetrofitHelper
import com.newsappmvvm.repository.NewsRepository

class MyApplication:Application() {
lateinit var newsRepository: NewsRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val newsServices = RetrofitHelper.getInstance().create(NewsService::class.java)
         newsRepository = NewsRepository(newsServices, applicationContext)
    }
}