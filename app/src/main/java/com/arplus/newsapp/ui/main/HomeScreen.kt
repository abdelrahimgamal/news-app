package com.arplus.newsapp.ui.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.arplus.newsapp.ui.views.ArticleItemUI

@Composable
fun HomeScreen(
    viewModel: ArticlesViewModel = hiltViewModel()
) {
    val articles = viewModel.pagingFlow.collectAsLazyPagingItems()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val context = LocalContext.current
        LaunchedEffect(key1 = articles.loadState) {
            if (articles.loadState.refresh is LoadState.Error) {
                Toast.makeText(
                    context,
                    "Error: " + (articles.loadState.refresh as LoadState.Error).error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (articles.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(articles) { article ->
                        article?.let {
                            ArticleItemUI(article = article) {
                                viewModel.updateFavorite(it)
                            }
                        }

                    }
                    item {
                        if (articles.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }

}