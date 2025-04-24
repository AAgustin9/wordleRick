package com.example.wordlerick.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordlerick.api.Character
import com.example.wordlerick.apiManager.ApiServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiServiceImpl
) : ViewModel() {

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

    private val _correctAnswer = MutableStateFlow<String?>(null)
    val correctAnswer: StateFlow<String?> = _correctAnswer.asStateFlow()

    private val _questionsList = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val questionsList: StateFlow<List<QuizQuestion>> = _questionsList.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow(false)
    val error: StateFlow<Boolean> = _error.asStateFlow()

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        _loading.value = true
        apiService.getCharacters(
            context = context,
            onSuccess = { characters ->
                viewModelScope.launch {
                    prepareQuestions(characters)
                    _loading.value = false
                }
            },
            onFail = {
                _error.value = true
                _loading.value = false
            },
            loadingFinished = {
                // manejado con el onSuccess/onFail
            }
        )
    }

    private fun prepareQuestions(characters: List<Character>) {
        val shuffledCharacters = characters.shuffled()
        val newQuestions = mutableListOf<QuizQuestion>()

        for (i in 0 until minOf(10, shuffledCharacters.size)) {
            val correctCharacter = shuffledCharacters[i]

            val wrongOptions = (characters - correctCharacter).shuffled().take(3).map { it.name }

            val options = wrongOptions.toMutableList()
            options.add(Random.nextInt(0, 4), correctCharacter.name)

            newQuestions.add(
                QuizQuestion(
                    characterImage = correctCharacter.image,
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
        _correctAnswer.value = null
        _isAnswerLocked.value = false

        if (_currentQuestionIndex.value < _questionsList.value.size - 1) {
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
        _correctAnswer.value = null
        loadCharacters()
    }
}

data class QuizQuestion(
    val characterImage: String,
    val options: List<String>,
    val correctAnswer: String
) 