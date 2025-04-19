package com.example.wordlerick

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.example.wordlerick.ui.navigation.BottomBar
import com.example.wordlerick.ui.navigation.NavHostComposable
import com.example.wordlerick.ui.screens.GameApp
import com.example.wordlerick.ui.theme.WordleRickTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            WordleRickTheme {
             Scaffold(
                 containerColor = Color.White,
                 modifier = Modifier.fillMaxSize(),
                 bottomBar = { BottomBar(navController::navigate) }
             ) { innerPadding ->
                 NavHostComposable(innerPadding, navController)
             }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    WordleRickTheme {
        GameApp()
    }
}

