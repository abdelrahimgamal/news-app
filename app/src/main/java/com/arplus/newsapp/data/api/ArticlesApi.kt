package com.arplus.newsapp.data.api

import com.arplus.newsapp.BuildConfig
import com.arplus.newsapp.data.model.ApiContainer
import retrofit2.http.GET
import retrofit2.http.Query


interface ArticlesApi {

    @GET(ApiConstants.EVERY_THING)
    suspend fun getArticles(
        @Query("q") query: String = "bitcoin",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int,
        @Query("pageSize") pageCount: Int
    ): ApiContainer


}