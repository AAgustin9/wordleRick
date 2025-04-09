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
//class CharacterViewModel @Inject constructor(
//    private val characterRepository: CharacterRepository
//) : ViewModel() {
//
//    private val _characters = mutableStateOf<List<Character>>(emptyList())
//    val characters: State<List<Character>> = _characters
//
//    private val _isLoading = mutableStateOf(false)
//    val isLoading: State<Boolean> = _isLoading
//
//    fun searchCharacters(query: String) {
//        viewModelScope.launch {
//            _isLoading.value = true
//            _characters.value = characterRepository.searchCharacters(query)
//            _isLoading.value = false
//        }
//    }
//}