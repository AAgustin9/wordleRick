package com.example.wordlerick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wordlerick.ui.CharacterImage
import com.example.wordlerick.ui.SelectionOptions
import com.example.wordlerick.ui.SubmitButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CharacterGuessingGame()
        }
    }
}

@Composable
fun CharacterGuessingGame() {
    var selectedMake by remember { mutableStateOf("") }
    var selectedModel by remember { mutableStateOf("") }
    var selectedYear by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Look Morty, I turned myself into a Wordle!")
        Spacer(modifier = Modifier.height(16.dp))

        CharacterImage()
        SelectionOptions(
            { selectedMake = it },
            { selectedModel = it },
            { selectedYear = it },
            { isButtonEnabled = it }
        )
        SubmitButton(isEnabled = isButtonEnabled)
    }
}
