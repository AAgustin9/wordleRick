package com.example.wordlerick.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.wordlerick.R
import com.example.wordlerick.ui.screens.QuizQuestion
import com.example.wordlerick.ui.screens.ShowCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class GameViewModel : ViewModel() {
    // Mock list of all characters in the game
    private val allCharacters = listOf(
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

    // Game state
    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    private val _gameOver = MutableStateFlow(false)
    val gameOver: StateFlow<Boolean> = _gameOver.asStateFlow()

    private val _isAnswerLocked = MutableStateFlow(false)
    val isAnswerLocked: StateFlow<Boolean> = _isAnswerLocked.asStateFlow()

    private val _selectedOption = MutableStateFlow<String?>(null)
    val selectedOption: StateFlow<String?> = _selectedOption.asStateFlow()

    private val _correctAnswer = MutableStateFlow("")
    val correctAnswer: StateFlow<String> = _correctAnswer.asStateFlow()

    private val _questionsList = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val questionsList: StateFlow<List<QuizQuestion>> = _questionsList.asStateFlow()

    init {
        prepareQuestions()
    }

    private fun prepareQuestions() {
        val shuffledCharacters = allCharacters.shuffled()
        val newQuestions = mutableListOf<QuizQuestion>()

        for (i in 0 until 10) {
            val correctCharacter = shuffledCharacters[i]

            // Generate 3 wrong options
            val wrongOptions = (allCharacters - correctCharacter).shuffled().take(3).map { it.name }

            // Create options with correct answer in random position
            val options = wrongOptions.toMutableList()
            options.add(Random.nextInt(0, 4), correctCharacter.name)

            newQuestions.add(
                QuizQuestion(
                    characterImageResId = correctCharacter.imageResId,
                    options = options,
                    correctAnswer = correctCharacter.name
                )
            )
        }
        _questionsList.value = newQuestions
    }

    fun getCurrentQuestion(): QuizQuestion {
        return _questionsList.value[_currentQuestionIndex.value]
    }

    fun checkAnswer(selectedOption: String) {
        if (_isAnswerLocked.value) return

        _selectedOption.value = selectedOption
        _isAnswerLocked.value = true
        _correctAnswer.value = getCurrentQuestion().correctAnswer

        if (selectedOption == _correctAnswer.value) {
            _score.value += 10
        }
    }

    fun moveToNextQuestion() {
        _selectedOption.value = null
        _isAnswerLocked.value = false

        if (_currentQuestionIndex.value < 9) {
            _currentQuestionIndex.value++
        } else {
            _gameOver.value = true
        }
    }

    fun restartGame() {
        _currentQuestionIndex.value = 0
        _score.value = 0
        _gameOver.value = false
        _isAnswerLocked.value = false
        _selectedOption.value = null
        prepareQuestions()
    }
} 