package com.example.wordlerick.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordlerick.ui.theme.DarkBlue

@Composable
fun QuizHeader(questionNumber: Int, totalQuestions: Int, score: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Question",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue
            )
            Text(
                text = "$questionNumber/$totalQuestions",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Score",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue
            )
            Text(
                text = "$score",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}