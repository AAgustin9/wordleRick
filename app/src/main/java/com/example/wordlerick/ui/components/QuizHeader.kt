package com.example.wordlerick.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.wordlerick.ui.theme.DarkBlue
import com.example.wordlerick.ui.theme.defaultSize
import com.example.wordlerick.ui.theme.size18
import com.example.wordlerick.ui.theme.size22

@Composable
fun QuizHeader(questionNumber: Int, totalQuestions: Int, score: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(defaultSize),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Question",
                fontSize = size18,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$questionNumber/$totalQuestions",
                fontSize = size22,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Score",
                fontSize = size18,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$score",
                fontSize = size22,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}