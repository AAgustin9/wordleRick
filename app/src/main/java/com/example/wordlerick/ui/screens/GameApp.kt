package com.example.wordlerick.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.wordlerick.ui.components.QuizHeader
import com.example.wordlerick.ui.components.QuestionCard
import com.example.wordlerick.ui.components.AnswerOptions
import com.example.wordlerick.ui.components.GameResults
import com.example.wordlerick.ui.screens.SaveScoreScreen
import com.example.wordlerick.ui.navigation.WordleRickScreen
import com.example.wordlerick.ui.viewmodels.GameViewModel
import com.example.wordlerick.ui.viewmodels.LeaderboardViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import com.example.wordlerick.ui.theme.defaultSize
import com.example.wordlerick.ui.theme.sizeBig3

@Composable
fun GameApp(
    navController: NavHostController,
    viewModel: GameViewModel = hiltViewModel()
) {
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
                    modifier = Modifier.size(sizeBig3),
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
                    Spacer(modifier = Modifier.height(defaultSize))
                    Button(
                        onClick = { viewModel.restartGame() }
                    ) {
                        Text("Retry")
                    }
                }
            }
            gameOver -> {
                val score by viewModel.score.collectAsState()
                val leaderboardViewModel: LeaderboardViewModel = hiltViewModel()
                SaveScoreScreen(
                    score = score,
                    onSave = { playerName ->
                        leaderboardViewModel.insertEntry(playerName, score)
                        navController.navigate(WordleRickScreen.Leaderboard.name)
                    },
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
    val questionsList by viewModel.questionsList.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val score by viewModel.score.collectAsState()
        QuizHeader(
            questionNumber = currentQuestionIndex + 1,
            totalQuestions = questionsList.size,
            score = score
        )

        QuestionCard(
            imageUrl = currentQuestion.characterImage,
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
