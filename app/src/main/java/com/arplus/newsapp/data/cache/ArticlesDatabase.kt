package com.arplus.newsapp.data.cache


import androidx.room.Database
import androidx.room.RoomDatabase
import com.arplus.newsapp.data.cache.dao.ArticlesDao
import com.arplus.newsapp.data.cache.model.CachedArticle
import com.arplus.newsapp.data.cache.model.CachedFavoriteArticle

@Database(
    entities = [
        CachedArticle::class,
        CachedFavoriteArticle::class
    ],
    version = 6
)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
}