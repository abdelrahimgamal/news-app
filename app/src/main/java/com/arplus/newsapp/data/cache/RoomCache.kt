package com.arplus.newsapp.data.cache


import androidx.paging.PagingSource
import com.arplus.newsapp.data.cache.dao.ArticlesDao
import com.arplus.newsapp.data.cache.model.CachedArticle
import com.arplus.newsapp.data.cache.model.CachedFavoriteArticle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val articlesDao: ArticlesDao,
) : Cache {
    override  fun getArticles(): PagingSource<Int, CachedArticle> =
        articlesDao.getAllArticles()

    override  fun deleteArticles() = articlesDao.clearArticles()


    override suspend fun storeArticles(vararg articles: CachedArticle) =
        articlesDao.insertArticle(*articles)

    override  fun getFavoriteArticles(): Flow<List<CachedFavoriteArticle>> =
        articlesDao.getAllFavorites()


    override suspend fun unFavoriteArticle(article: CachedFavoriteArticle) =
        articlesDao.unFavorite(article)

    override suspend fun isFavorite(url:String) =articlesDao.isFav(url)


    override suspend fun favoriteArticle(article: CachedFavoriteArticle) =
        articlesDao.favoriteArticle(article)


}