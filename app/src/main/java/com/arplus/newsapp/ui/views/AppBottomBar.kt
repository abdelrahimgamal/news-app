package com.arplus.newsapp.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.arplus.newsapp.ui.navigation.NavigationRoutes

val routes = listOf(
    NavigationRoutes.HomeRoute,
    NavigationRoutes.FavoriteRoute
)

@Composable
fun AppBottomBar(navController: NavHostController) {


    NavigationBar(
        containerColor = Color.Transparent,
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .background(Color.White)
    ) {
        routes.forEach { route ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(route.icon),
                        contentDescription = route.route, modifier = Modifier.size(25.dp)
                    )
                },
                alwaysShowLabel = navController.currentBackStackEntryAsState().value?.destination?.route == route.route,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color.White,
                    selectedTextColor = Color.Black
                ),
                label = { Text(text = route.route) },
                selected = navController.currentBackStackEntryAsState().value?.destination?.route == route.route,
                onClick = {
                    navController.navigate(route.route)
                }
            )
        }
    }
}
