package com.arplus.newsapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arplus.newsapp.ui.navigation.AppNavHost
import com.arplus.newsapp.ui.theme.NewsAppTheme
import com.arplus.newsapp.ui.views.AppBottomBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                val navController: NavHostController = rememberNavController()
                Scaffold(bottomBar = { AppBottomBar(navController = navController) }) {
                    Box(modifier = Modifier.padding(it).background(Color.White)) {
                        AppNavHost(navController = navController)
                    }
                }
            }
        }
    }
}

