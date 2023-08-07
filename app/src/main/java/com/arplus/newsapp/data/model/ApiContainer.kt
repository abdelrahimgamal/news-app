package com.arplus.newsapp.data.model


data class ApiContainer(
    val status: String, val totalResults: Int, val articles: List<ApiArticle>
)
