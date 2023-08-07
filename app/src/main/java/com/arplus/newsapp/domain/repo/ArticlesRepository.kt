package com.arplus.newsapp.domain.repo

import androidx.paging.PagingData
import com.arplus.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow


interface ArticlesRepository {
    fun getArticles(): Flow<PagingData<Article>>
     fun getFavorites(): Flow<List<Article>>
    suspend fun updateFavStatus(article: Article)
}