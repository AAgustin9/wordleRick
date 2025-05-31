package com.example.wordlerick.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wordlerick.api.Character
import com.example.wordlerick.ui.screens.CharacterDetailScreen
import com.example.wordlerick.ui.screens.GameApp
import com.example.wordlerick.ui.screens.LeaderboardScreen
import com.example.wordlerick.ui.screens.UserScreen
import com.example.wordlerick.ui.screens.WikiScreen
import com.example.wordlerick.ui.theme.sizeBig1
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun NavHostComposable(innerPadding: PaddingValues, navController: NavHostController) {
    var selectedCharacter by remember { mutableStateOf<Character?>(null) }

    NavHost(
        navController = navController,
        startDestination = WordleRickScreen.Home.name,
        modifier = Modifier.fillMaxSize().padding(innerPadding).padding(sizeBig1)
    ) {
        composable(route = WordleRickScreen.Home.name) {
            GameApp(navController)
        }

        composable(route = WordleRickScreen.Wiki.name) {
            if (selectedCharacter == null) {
                WikiScreen(
                    onCharacterClick = { character ->
                        selectedCharacter = character
                    }
                )
            } else {
                CharacterDetailScreen(
                    character = selectedCharacter!!,
                    onBackClick = {
                        selectedCharacter = null
                    }
                )
            }
        }

        composable(route = WordleRickScreen.User.name) {
            UserScreen()
        }

        composable(route = WordleRickScreen.Leaderboard.name) {
            LeaderboardScreen()
        }
    }

}