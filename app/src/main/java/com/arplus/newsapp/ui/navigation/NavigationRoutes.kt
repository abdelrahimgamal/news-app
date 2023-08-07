package com.arplus.newsapp.ui.navigation

import com.arplus.newsapp.R


sealed class NavigationRoutes(val route: String, val icon: Int) {
    object HomeRoute : NavigationRoutes("home", R.drawable.baseline_home_24)
    object FavoriteRoute : NavigationRoutes("favorite", R.drawable.baseline_favorite_24)
}


