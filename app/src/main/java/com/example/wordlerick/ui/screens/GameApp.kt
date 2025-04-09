package com.example.wordlerick.ui.screens

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordlerick.ui.components.QuizHeader
import com.example.wordlerick.ui.components.QuestionCard
import com.example.wordlerick.ui.components.AnswerOptions
import com.example.wordlerick.ui.components.GameResults
import com.example.wordlerick.ui.viewmodels.GameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun GameApp(viewModel: GameViewModel = viewModel()) {
    val gameOver by viewModel.gameOver.collectAsState()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        if (gameOver) {
            val score by viewModel.score.collectAsState()
            GameResults(
                score = score,
                onRestart = { viewModel.restartGame() }
            )
        } else {
            GameScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun GameScreen(modifier: Modifier = Modifier, viewModel: GameViewModel = viewModel()) {
    val currentQuestion = viewModel.getCurrentQuestion()
    val coroutineScope = rememberCoroutineScope()
    val selectedOption by viewModel.selectedOption.collectAsState()
    val isAnswerLocked by viewModel.isAnswerLocked.collectAsState()
    val correctAnswer by viewModel.correctAnswer.collectAsState()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val score by viewModel.score.collectAsState()
        QuizHeader(
            questionNumber = currentQuestionIndex + 1,
            totalQuestions = 10,
            score = score
        )

        QuestionCard(
            imageResId = currentQuestion.characterImageResId,
            question = "Who is this character?"
        )

        AnswerOptions(
            options = currentQuestion.options,
            selectedOption = selectedOption,
            correctAnswer = if (isAnswerLocked) correctAnswer else null,
            onOptionSelected = { option ->
                viewModel.checkAnswer(option)
                coroutineScope.launch {
                    delay(1500)
                    viewModel.moveToNextQuestion()
                }
            }
        )
    }
}

data class ShowCharacter(
    val id: Int,
    val name: String,
    val imageResId: Int
)

data class QuizQuestion(
    val characterImageResId: Int,
    val options: List<String>,
    val correctAnswer: String
)
