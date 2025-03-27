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
import com.example.wordlerick.ui.GuessingGrid

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
    var selectedName by remember { mutableStateOf("") }
    var selectedAge by remember { mutableStateOf("") }
    var selectedRace by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }
    var guesses by remember { mutableStateOf(List(5) { listOf<Boolean?>(null, null, null) }) }
    var currentRow by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Look Morty, I turned myself into a Wordle!")
        Spacer(modifier = Modifier.height(16.dp))

        CharacterImage()
        GuessingGrid(guesses = guesses)
        SelectionOptions(
            { selectedName = it },
            { selectedAge = it },
            { selectedRace = it },
            { isButtonEnabled = it }
        )

        SubmitButton(isEnabled = isButtonEnabled, onClick = {
            if (currentRow < 5) {
                // Simulating checking the guess (replace this with actual logic)
                val newGuess = listOf(
                    selectedName == "Rick", // Example: checking if the name is "Rick"
                    selectedAge == "70",   // Checking if age is "70"
                    selectedRace == "Human" // Checking if race is "Human"
                )

                guesses = guesses.toMutableList().apply {
                    this[currentRow] = newGuess
                }

                currentRow++
            }
        })
    }
}
