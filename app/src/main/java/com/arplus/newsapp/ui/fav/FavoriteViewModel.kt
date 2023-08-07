package com.arplus.newsapp.ui.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arplus.newsapp.domain.model.Article
import com.arplus.newsapp.domain.usecase.GetFavorites
import com.arplus.newsapp.domain.usecase.UpdateFavStatus
import com.arplus.newsapp.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavorites: GetFavorites,
    private val updateFavStatus: UpdateFavStatus,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>?>(null)
    val articles: StateFlow<List<Article>?> =
        _articles.asStateFlow()
    private val _connectionOff = MutableStateFlow<Boolean>(false)
    val connectionOff: StateFlow<Boolean> =
        _connectionOff.asStateFlow()


    init {
        subscribeToFavoritesUpdates()
    }


    private fun subscribeToFavoritesUpdates() {
        viewModelScope.launch(mainDispatcher) {
            getFavorites().collect {
                onNewArticlesList(it)
            }
        }
    }

    private suspend fun onNewArticlesList(articles: List<Article>) {
        Logger.d("Got more Articles $articles")
        _articles.emit(articles)
    }

    fun updateFavorite(article: Article) {
        viewModelScope.launch(mainDispatcher) {
            updateFavStatus(article)
        }
    }
}