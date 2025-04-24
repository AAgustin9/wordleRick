package com.example.wordlerick.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wordlerick.ui.components.QuizHeader
import com.example.wordlerick.ui.components.QuestionCard
import com.example.wordlerick.ui.components.AnswerOptions
import com.example.wordlerick.ui.components.GameResults
import com.example.wordlerick.ui.viewmodels.GameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

@Composable
fun GameApp(viewModel: GameViewModel = hiltViewModel()) {
    val gameOver by viewModel.gameOver.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        when {
            loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Error loading characters",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.restartGame() }
                    ) {
                        Text("Retry")
                    }
                }
            }
            gameOver -> {
                val score by viewModel.score.collectAsState()
                GameResults(
                    score = score,
                    onRestart = { viewModel.restartGame() }
                )
            }
            else -> {
                GameScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun GameScreen(modifier: Modifier = Modifier, viewModel: GameViewModel = hiltViewModel()) {
    val currentQuestion = viewModel.getCurrentQuestion()
    val coroutineScope = rememberCoroutineScope()
    val selectedOption by viewModel.selectedOption.collectAsState()
    val isAnswerLocked by viewModel.isAnswerLocked.collectAsState()
    val correctAnswer by viewModel.correctAnswer.collectAsState()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()

    var answerRevealed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val score by viewModel.score.collectAsState()
        QuizHeader(
            questionNumber = currentQuestionIndex + 1,
            totalQuestions = viewModel.questionsList.value.size,
            score = score
        )

        QuestionCard(
            question = "Who is this character?",
            imageUrl = currentQuestion.characterImage,
        )

        AnswerOptions(
            options = currentQuestion.options,
            selectedOption = selectedOption,
            correctAnswer = if (isAnswerLocked && answerRevealed) correctAnswer else null,
            onOptionSelected = { option ->
                answerRevealed = true
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
