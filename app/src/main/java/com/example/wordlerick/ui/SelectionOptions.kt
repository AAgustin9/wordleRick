package com.example.wordlerick.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectionOptions(
    onCharacterNameSelected: (String) -> Unit,
    onSpeciesSelected: (String) -> Unit,
    onOriginSelected: (String) -> Unit,
    updateButtonState: (Boolean) -> Unit
) {
    var characterName by remember { mutableStateOf("") }
    var species by remember { mutableStateOf("") }
    var origin by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

        OutlinedTextField(
            value = characterName,
            onValueChange = {
                characterName = it
                onCharacterNameSelected(it)
                updateButtonState(characterName.isNotEmpty() && species.isNotEmpty() && origin.isNotEmpty())
            },
            label = { Text("Character Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = species,
            onValueChange = {
                species = it
                onSpeciesSelected(it)
                updateButtonState(characterName.isNotEmpty() && species.isNotEmpty() && origin.isNotEmpty())
            },
            label = { Text("Species") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = origin,
            onValueChange = {
                origin = it
                onOriginSelected(it)
                updateButtonState(characterName.isNotEmpty() && species.isNotEmpty() && origin.isNotEmpty())
            },
            label = { Text("Origin") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
