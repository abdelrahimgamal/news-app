package com.arplus.newsapp.domain.usecase

import com.arplus.newsapp.domain.repo.ArticlesRepository
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetFavorites @Inject constructor(private val articlesRepository: ArticlesRepository) {

     operator fun invoke() = articlesRepository.getFavorites()
}