package com.example.wordlerick.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip

@Composable
fun GuessingGrid(rows: Int = 5, guesses: List<List<Boolean?>>) {
    val columns = listOf("Name", "Age", "Race")

    Column(modifier = Modifier.padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            columns.forEach { Text(it) }
        }
        Spacer(Modifier.height(8.dp))

        guesses.forEach { row ->
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                row.forEach { isCorrect ->
                    val color = when (isCorrect) {
                        true -> Color.Green
                        false -> Color.Red
                        null -> Color.White
                    }
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Preview
@Composable
fun PreviewGuessingGrid() {
    GuessingGrid(
        guesses = List(5) { listOf(null, null, null) }
    )
}
