package com.example.wordlerick.ui.screens

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.wordlerick.R
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordlerick.ui.components.QuizHeader
import com.example.wordlerick.ui.components.QuestionCard
import com.example.wordlerick.ui.components.AnswerOptions
import com.example.wordlerick.ui.components.GameResults
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun GameApp(viewModel: GameViewModel = viewModel()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        if (viewModel.gameOver) {
            GameResults(
                score = viewModel.score,
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

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuizHeader(
            questionNumber = viewModel.currentQuestionIndex + 1,
            totalQuestions = 10,
            score = viewModel.score
        )

        QuestionCard(
            imageResId = currentQuestion.characterImageResId,
            question = "Who is this character?"
        )

        AnswerOptions(
            options = currentQuestion.options,
            selectedOption = viewModel.selectedOption,
            correctAnswer = if (viewModel.isAnswerLocked) viewModel.correctAnswer else null,
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

class GameViewModel : ViewModel() {
    // Mock list of all characters in the game
    val allCharacters = listOf(
        ShowCharacter(1, "Rick Sanchez", R.drawable.rick),
        ShowCharacter(2, "Morty Smith", R.drawable.morty),
        ShowCharacter(3, "Summer Smith", R.drawable.summer),
        ShowCharacter(4, "Beth Smith", R.drawable.beth),
        ShowCharacter(5, "Jerry Smith", R.drawable.jerry),
        ShowCharacter(6, "Birdperson", R.drawable.morty),
        ShowCharacter(7, "Squanchy", R.drawable.morty),
        ShowCharacter(8, "Mr. Meeseeks", R.drawable.morty),
        ShowCharacter(9, "Evil Morty", R.drawable.morty),
        ShowCharacter(10, "Mr. Poopybutthole", R.drawable.morty)
    )

    // manejo de game state
    var currentQuestionIndex by mutableIntStateOf(0)
    var score by mutableStateOf(0)
    var gameOver by mutableStateOf(false)
    var isAnswerLocked by mutableStateOf(false)
    var selectedOption by mutableStateOf<String?>(null)
    var correctAnswer by mutableStateOf("")

    private var questionsList = mutableStateListOf<QuizQuestion>()

    init {
        prepareQuestions()
    }

    private fun prepareQuestions() {
        val shuffledCharacters = allCharacters.shuffled()

        questionsList.clear()
        for (i in 0 until 10) {
            val correctCharacter = shuffledCharacters[i]

            // Generate 3 wrong options
            val wrongOptions = (allCharacters - correctCharacter).shuffled().take(3).map { it.name }

            // Create options with correct answer in random position
            val options = wrongOptions.toMutableList()
            options.add(Random.nextInt(0, 4), correctCharacter.name)

            questionsList.add(
                QuizQuestion(
                    characterImageResId = correctCharacter.imageResId,
                    options = options,
                    correctAnswer = correctCharacter.name
                )
            )
        }
    }

    fun getCurrentQuestion(): QuizQuestion {
        return questionsList[currentQuestionIndex]
    }

    fun checkAnswer(selectedOption: String) {
        if (isAnswerLocked) return

        this.selectedOption = selectedOption
        isAnswerLocked = true
        correctAnswer = getCurrentQuestion().correctAnswer

        if (selectedOption == correctAnswer) {
            score += 10
        }
    }

    fun moveToNextQuestion() {
        selectedOption = null
        isAnswerLocked = false

        if (currentQuestionIndex < 9) {
            currentQuestionIndex++
        } else {
            gameOver = true
        }
    }

    fun restartGame() {
        currentQuestionIndex = 0
        score = 0
        gameOver = false
        isAnswerLocked = false
        selectedOption = null
        prepareQuestions()
    }
}
