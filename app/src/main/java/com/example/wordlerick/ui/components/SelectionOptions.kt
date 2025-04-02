package com.example.wordlerick.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectionOptions(
    onCharacterNameSelected: (String) -> Unit,
    onAgeSelected: (String) -> Unit,
    onRaceSelected: (String) -> Unit,
    updateButtonState: (Boolean) -> Unit
) {
    var characterName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var race by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

        OutlinedTextField(
            value = characterName,
            onValueChange = {
                characterName = it
                onCharacterNameSelected(it)
                updateButtonState(characterName.isNotEmpty() && age.isNotEmpty() && race.isNotEmpty())
            },
            label = { Text("Character Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = age,
            onValueChange = {
                age = it
                onAgeSelected(it)
                updateButtonState(characterName.isNotEmpty() && age.isNotEmpty() && race.isNotEmpty())
            },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = race,
            onValueChange = {
                race = it
                onRaceSelected(it)
                updateButtonState(characterName.isNotEmpty() && age.isNotEmpty() && race.isNotEmpty())
            },
            label = { Text("Race") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
