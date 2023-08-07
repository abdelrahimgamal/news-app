
package com.arplus.newsapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arplus.newsapp.ui.fav.FavoriteScreen
import com.arplus.newsapp.ui.main.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.HomeRoute.route
    ) {


        composable(route = NavigationRoutes.HomeRoute.route) {
            HomeScreen()
        }

        composable(route = NavigationRoutes.FavoriteRoute.route) {
            FavoriteScreen()
        }

    }
}
