package com.example.wordlerick.api

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordlerick.apiManager.ApiServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiServiceImpl
): ViewModel() {

    private var _characters = MutableStateFlow(listOf<Character>())
    val characters = _characters.asStateFlow()

    private var _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    private var _showRetry = MutableStateFlow(false)
    val showRetry = _showRetry.asStateFlow()

    init {
        loadCharacters()
    }

    fun retryApiCall() {
        loadCharacters()
    }

    private fun loadCharacters() {
        _loading.value = true
        apiService.getCharacters(
            context = context,
            onSuccess = {
                viewModelScope.launch {
                    _characters.emit(it)
                }
                _showRetry.value = false
            },
            onFail = {
                _showRetry.value = true
            },
            loadingFinished = {
                _loading.value = false
            }
        )
    }
}