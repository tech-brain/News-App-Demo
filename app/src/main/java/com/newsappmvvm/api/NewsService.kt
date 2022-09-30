package com.newsappmvvm.api


import com.newsappmvvm.model.NewsData
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response


interface NewsService {

    @GET(TOP_HEADLINES)
    suspend fun getNews(@Query("country") country : String, @Query("category") category : String, @Query("apiKey") apiKey : String) :Response<NewsData>
}