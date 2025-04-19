package com.example.wordlerick.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wordlerick.ui.screens.GameApp
import com.example.wordlerick.ui.screens.UserScreen
import com.example.wordlerick.ui.screens.WikiScreen

@Composable
fun NavHostComposable(innerPadding: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = WordleRickScreen.Home.name,
        modifier = Modifier.fillMaxSize().padding(innerPadding).padding(20.dp)
    ) {
        composable(route = WordleRickScreen.Home.name) {
            GameApp()
        }

        composable(route = WordleRickScreen.Wiki.name) {
            WikiScreen()
        }

        composable(route = WordleRickScreen.User.name) {
            UserScreen()
        }
    }

}