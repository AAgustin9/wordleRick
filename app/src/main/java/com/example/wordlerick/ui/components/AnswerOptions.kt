package com.example.wordlerick.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.wordlerick.ui.theme.CorrectGreen
import com.example.wordlerick.ui.theme.DarkBlue
import com.example.wordlerick.ui.theme.IncorrectRed
import com.example.wordlerick.ui.theme.halfDefault
import com.example.wordlerick.ui.theme.size18

@Composable
fun AnswerOptions(
    options: List<String>,
    selectedOption: String?,
    correctAnswer: String?,
    onOptionSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = halfDefault),
        verticalArrangement = Arrangement.spacedBy(halfDefault)
    ) {
        options.forEach { option ->
            val isSelected = option == selectedOption
            val isCorrect = option == correctAnswer
            val backgroundColor = when {
                correctAnswer != null && isCorrect -> CorrectGreen
                isSelected && correctAnswer != null && !isCorrect -> IncorrectRed
                else -> DarkBlue
            }

            Button(
                onClick = { onOptionSelected(option) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    disabledContainerColor = backgroundColor
                ),
                enabled = correctAnswer == null
            ) {
                Text(
                    text = option,
                    fontSize = size18,
                    color = Color.White,
                    modifier = Modifier.padding(halfDefault)
                )
            }
        }
    }
}