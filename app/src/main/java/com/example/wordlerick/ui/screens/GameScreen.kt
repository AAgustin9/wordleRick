package com.example.wordlerick.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.wordlerick.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement.Vertical
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordlerick.ui.theme.DarkBlue

@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuizHeader(questionNumber = 1, score = 0)
        QuestionCard(imageRes = R.drawable.morty, question = "Who is this character?")
        AnswerOptions(
            options = listOf("Donna Gueterman", "Chris", "Gazorpazorpfield", "Hammerhead Morty"),
            onOptionSelected = {}
        )
    }
}

@Composable
fun QuizHeader(questionNumber: Int, score: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Question\n$questionNumber/10", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkBlue)
        Text(text = "Score\n$score", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkBlue)
    }
}

@Composable
fun QuestionCard(imageRes: Int, question: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(0.93f).padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = DarkBlue)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = question,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(14.dp).fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(6.dp),
                colors = CardDefaults.cardColors(containerColor = DarkBlue)
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Character Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(250.dp)
                )
            }
        }
    }
}

@Composable
fun AnswerOptions(options: List<String>, onOptionSelected: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(0.9f)) {
        options.forEach { option ->
            Button(
                onClick = { onOptionSelected(option) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
            ) {
                Text(text = option, fontSize = 18.sp, color = Color.White)
            }
        }
    }
}