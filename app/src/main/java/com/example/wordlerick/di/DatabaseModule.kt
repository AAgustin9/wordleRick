package com.example.wordlerick.di

import android.content.Context
import androidx.room.Room
import com.example.wordlerick.data.LeaderboardDao
import com.example.wordlerick.data.LeaderboardDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LeaderboardDatabase {
        return Room.databaseBuilder(
            context,
            LeaderboardDatabase::class.java,
            "leaderboard_db"
        ).build()
    }

    @Provides
    fun provideLeaderboardDao(db: LeaderboardDatabase): LeaderboardDao {
        return db.leaderboardDao()
    }
} 