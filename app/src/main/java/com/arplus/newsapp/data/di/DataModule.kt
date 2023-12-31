package com.arplus.newsapp.data.di

import com.arplus.newsapp.data.repoimp.ArticlesRepositoryImp
import com.arplus.newsapp.domain.repo.ArticlesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DataModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindArticlesRepository(repository: ArticlesRepositoryImp): ArticlesRepository

}