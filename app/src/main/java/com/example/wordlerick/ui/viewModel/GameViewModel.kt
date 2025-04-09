//package com.example.wordlerick.ui.viewModel
//
//import androidx.compose.runtime.State
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class GameViewModel @Inject constructor(
//    //private val characterRepository: CharacterRepository,
//) : ViewModel() {
//
//    private val _currentCharacter = mutableStateOf<Character?>(null)
//    val currentCharacter: State<Character?> = _currentCharacter
//
//    private val _score = mutableStateOf(0)
//    val score: State<Int> = _score
//
//    private val _gameOver = mutableStateOf(false)
//    val gameOver = _gameOver
//
//    init {
//        loadNewCharacter()
//    }
//
//    fun loadNewCharacter() {
//        viewModelScope.launch {
//            _currentCharacter.value = characterRepository.getRandomCharacter()
//            _gameOver.value = false
//        }
//    }
//
//    fun increaseScore() {
//        _score.value += 1
//    }
//}