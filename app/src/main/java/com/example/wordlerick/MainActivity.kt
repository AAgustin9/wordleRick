package com.example.wordlerick

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wordlerick.ui.navigation.BottomBar
import com.example.wordlerick.ui.navigation.NavHostComposable
import com.example.wordlerick.ui.theme.WordleRickTheme
import com.example.wordlerick.ui.viewmodels.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.example.wordlerick.ui.screens.GameApp
import com.google.firebase.FirebaseApp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import com.example.wordlerick.notifications.NotificationHelper

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Schedule daily reminder notification
        NotificationHelper(this).scheduleReminder(intervalMillis = 60_000L)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
            WordleRickTheme(darkTheme = isDarkTheme) {
                Scaffold(
                    topBar = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                Text(text = if (isDarkTheme) "Dark" else "Light")
                                Spacer(modifier = Modifier.height(4.dp))
                                Switch(
                                    checked = isDarkTheme,
                                    onCheckedChange = { themeViewModel.toggleTheme() }
                                )
                            }
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.background,
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
    val navController = rememberNavController()
    WordleRickTheme {
        GameApp(navController)
    }
}

