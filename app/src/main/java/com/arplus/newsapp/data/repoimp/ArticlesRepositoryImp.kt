package com.arplus.newsapp.data.repoimp

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.arplus.newsapp.data.cache.Cache
import com.arplus.newsapp.data.cache.model.CachedArticle
import com.arplus.newsapp.data.cache.model.CachedFavoriteArticle
import com.arplus.newsapp.data.cache.model.toDomain
import com.arplus.newsapp.domain.model.Article
import com.arplus.newsapp.domain.repo.ArticlesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticlesRepositoryImp @Inject constructor(
    private val cache: Cache,
    private val pager: Pager<Int, CachedArticle>,
) : ArticlesRepository {


    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    override fun getArticles(): Flow<PagingData<Article>> {
        return pager
            .flow
            .map { pagingData ->
                pagingData.map { it.toDomain() }
            }
            .cachedIn(coroutineScope)

    }

    override  fun getFavorites(): Flow<List<Article>> {
        return cache.getFavoriteArticles()
            .distinctUntilChanged() // ensures only events with new information get to the subscriber.
            .map { articlesList ->
                articlesList.map { it.toDomain() }
            }
    }

    override suspend fun updateFavStatus(article: Article) {
        val isFav = cache.isFavorite(article.url) == 1
        if (isFav)
            unFavorite(article)
        else
            addToFavite(article)

    }

    private suspend fun addToFavite(article: Article) {
        cache.favoriteArticle(CachedFavoriteArticle.fromDomain(article))
    }

    private suspend fun unFavorite(article: Article) {
        cache.unFavoriteArticle(CachedFavoriteArticle.fromDomain(article))
    }


}