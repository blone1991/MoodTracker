package com.self.moodtracker.di

import android.content.Context
import androidx.room.Room
import com.self.moodtracker.data.local.AppDatabase
import com.self.moodtracker.data.local.EmotionDao
import com.self.moodtracker.data.repository.EmotionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "MoodTrackerAppDatabase").build()

    @Provides
    @Singleton
    fun provideEmotionDao(appDatabase: AppDatabase) = appDatabase.emotionDao()

    @Provides
    @Singleton
    fun provideEmotionRepository(emotionDao: EmotionDao) = EmotionRepository(emotionDao)
}