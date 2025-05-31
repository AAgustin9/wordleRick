package com.example.wordlerick.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordlerick.data.LeaderboardEntry
import com.example.wordlerick.data.LeaderboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val repository: LeaderboardRepository
) : ViewModel() {

    val topScores: StateFlow<List<LeaderboardEntry>> = repository.getTopScores()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    fun insertEntry(playerName: String, score: Int) {
        viewModelScope.launch {
            val entry = LeaderboardEntry(playerName = playerName, score = score)
            repository.insert(entry)
        }
    }
} 