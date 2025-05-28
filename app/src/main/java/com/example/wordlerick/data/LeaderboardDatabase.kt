package com.example.wordlerick.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LeaderboardEntry::class], version = 1, exportSchema = false)
abstract class LeaderboardDatabase : RoomDatabase() {
    abstract fun leaderboardDao(): LeaderboardDao
} 