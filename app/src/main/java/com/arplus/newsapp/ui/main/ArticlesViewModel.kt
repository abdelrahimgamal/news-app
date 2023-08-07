package com.arplus.newsapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arplus.newsapp.domain.model.Article
import com.arplus.newsapp.domain.usecase.GetArticles
import com.arplus.newsapp.domain.usecase.UpdateFavStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    getArticles: GetArticles,
    private val updateFavStatus: UpdateFavStatus,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {


    val pagingFlow =getArticles()

    fun updateFavorite(article: Article) {
        viewModelScope.launch(mainDispatcher) {
            updateFavStatus(article)
        }
    }
}