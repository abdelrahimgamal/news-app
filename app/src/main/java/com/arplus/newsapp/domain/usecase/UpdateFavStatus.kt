package com.arplus.newsapp.domain.usecase

import com.arplus.newsapp.domain.model.Article
import com.arplus.newsapp.domain.repo.ArticlesRepository
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class UpdateFavStatus @Inject constructor(private val articlesRepository: ArticlesRepository) {

    suspend operator fun invoke(article: Article) = articlesRepository.updateFavStatus(article)
}