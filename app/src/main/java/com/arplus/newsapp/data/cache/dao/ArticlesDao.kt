package com.arplus.newsapp.data.cache.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.arplus.newsapp.data.cache.model.CachedArticle
import com.arplus.newsapp.data.cache.model.CachedFavoriteArticle
import kotlinx.coroutines.flow.Flow


@Dao
abstract class ArticlesDao {

    @Transaction
    @Query("SELECT * FROM articles ")
    abstract  fun getAllArticles(): PagingSource<Int, CachedArticle>


    @Query("DELETE FROM articles")
    abstract  fun clearArticles()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertArticle(vararg article: CachedArticle)

    @Transaction
    @Query("SELECT * FROM favorites ")
    abstract  fun getAllFavorites(): Flow<List<CachedFavoriteArticle>>


    @Delete
    abstract suspend fun unFavorite(article: CachedFavoriteArticle)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun favoriteArticle(article: CachedFavoriteArticle)

    @Query("SELECT EXISTS (SELECT 1 FROM favorites WHERE url=:url)")
    abstract suspend fun isFav(url: String): Int
}