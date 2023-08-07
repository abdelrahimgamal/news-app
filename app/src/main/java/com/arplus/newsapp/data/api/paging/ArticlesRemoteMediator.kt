package com.arplus.newsapp.data.api.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.arplus.newsapp.data.api.ArticlesApi
import com.arplus.newsapp.data.cache.ArticlesDatabase
import com.arplus.newsapp.data.cache.Cache
import com.arplus.newsapp.data.cache.model.CachedArticle
import com.arplus.newsapp.data.model.mapToDomain
import com.arplus.newsapp.utils.getPage
import com.arplus.newsapp.utils.savePage
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ArticlesRemoteMediator(
    private val cache: Cache,
    private val articlesDatabase: ArticlesDatabase,
    private val api: ArticlesApi,
) : RemoteMediator<Int, CachedArticle>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CachedArticle>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()

                    if (lastItem == null) 1 else getPage()
                }
            }

            val articles = api.getArticles(
                page = loadKey,
                pageCount = state.config.pageSize
            ).articles.map { it.mapToDomain() }

            articlesDatabase.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    cache.deleteArticles()
                }
                articles.distinct().forEach { article ->
                    val newArticle = article.copy(
                        isFavourite = cache.isFavorite(article.url) == 1,
                    )
                    cache.storeArticles(CachedArticle.fromDomain(newArticle))
                }
                savePage(loadKey + 1)
            }

            MediatorResult.Success(
                endOfPaginationReached = articles.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}