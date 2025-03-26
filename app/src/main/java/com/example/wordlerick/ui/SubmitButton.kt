package com.example.wordlerick.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SubmitButton(isEnabled: Boolean) {
    Button(
        onClick = { /* logica submit */ },
        enabled = isEnabled,
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Text("Submit")
    }
}
