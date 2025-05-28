package com.example.wordlerick.data

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LeaderboardRepository @Inject constructor(
    private val dao: LeaderboardDao
) {
    fun getTopScores(): Flow<List<LeaderboardEntry>> = dao.getTopScores()

    suspend fun insert(entry: LeaderboardEntry) = withContext(Dispatchers.IO) {
        dao.insert(entry)
    }
} 