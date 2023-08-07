package com.arplus.newsapp.data.di


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.arplus.newsapp.data.api.ApiConstants
import com.arplus.newsapp.data.api.ArticlesApi
import com.arplus.newsapp.data.api.interceptor.LoggingInterceptor
import com.arplus.newsapp.data.api.paging.ArticlesRemoteMediator
import com.arplus.newsapp.data.cache.ArticlesDatabase
import com.arplus.newsapp.data.cache.Cache
import com.arplus.newsapp.data.cache.model.CachedArticle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {


    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideArticlesPager(
        cache: Cache,
        articlesDatabase: ArticlesDatabase,
        articlesApi: ArticlesApi
    ): Pager<Int, CachedArticle> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = ArticlesRemoteMediator(
                api = articlesApi,
                articlesDatabase = articlesDatabase,
                cache = cache
            ),
            pagingSourceFactory = {
                cache.getArticles()
            }
        )
    }

    @Provides
    @Singleton
    fun provideApi(builder: Retrofit.Builder): ArticlesApi {
        return builder
            .build()
            .create(ArticlesApi::class.java)
    }


    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(loggingInterceptor: LoggingInterceptor): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(loggingInterceptor)

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }
}