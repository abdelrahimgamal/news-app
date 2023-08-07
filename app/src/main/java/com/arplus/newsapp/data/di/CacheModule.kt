package com.arplus.newsapp.data.di

import android.content.Context
import androidx.room.Room
import com.arplus.newsapp.data.cache.ArticlesDatabase
import com.arplus.newsapp.data.cache.Cache
import com.arplus.newsapp.data.cache.RoomCache
import com.arplus.newsapp.data.cache.dao.ArticlesDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    abstract fun bindCache(cache: RoomCache): Cache

    companion object {

        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): ArticlesDatabase {
            return Room.databaseBuilder(
                context,
                ArticlesDatabase::class.java,
                "articles.db"
            ).fallbackToDestructiveMigration()
                .build()
        }

        @Provides
        fun provideArticlesDao(
            articlesDatabase: ArticlesDatabase
        ): ArticlesDao = articlesDatabase.articlesDao()

    }
}