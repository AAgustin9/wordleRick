package com.example.wordlerick.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.wordlerick.ui.navigation.WordleRickScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor() : ViewModel() {
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    private val _currentScreen = MutableStateFlow(WordleRickScreen.Home)
    val currentScreen: StateFlow<WordleRickScreen> = _currentScreen.asStateFlow()

    fun onTabSelected(index: Int) {
        _selectedTabIndex.value = index
        _currentScreen.value = when (index) {
            0 -> WordleRickScreen.Home
            1 -> WordleRickScreen.Wiki
            2 -> WordleRickScreen.User
            else -> WordleRickScreen.Home
        }
    }
} 