package com.example.wordlerick.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordlerick.ui.theme.CorrectGreen
import com.example.wordlerick.ui.theme.DarkBlue
import com.example.wordlerick.ui.theme.IncorrectRed

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
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
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
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}