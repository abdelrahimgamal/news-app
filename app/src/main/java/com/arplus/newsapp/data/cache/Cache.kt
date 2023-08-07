package com.arplus.newsapp.data.cache

import androidx.paging.PagingSource
import com.arplus.newsapp.data.cache.model.CachedArticle
import com.arplus.newsapp.data.cache.model.CachedFavoriteArticle
import kotlinx.coroutines.flow.Flow

interface Cache {

    fun getArticles(): PagingSource<Int, CachedArticle>

     fun deleteArticles()

    suspend fun storeArticles(vararg articles: CachedArticle)

     fun getFavoriteArticles(): Flow<List<CachedFavoriteArticle>>

    suspend fun unFavoriteArticle(article: CachedFavoriteArticle)

    suspend fun isFavorite(url: String): Int

    suspend fun favoriteArticle(article: CachedFavoriteArticle)


}