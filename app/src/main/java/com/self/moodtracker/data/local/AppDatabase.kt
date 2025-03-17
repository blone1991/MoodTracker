package com.self.moodtracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.self.moodtracker.data.model.Emotion

@Database(entities = [Emotion::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun emotionDao() : EmotionDao
}