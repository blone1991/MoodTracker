package com.self.moodtracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.self.moodtracker.data.model.Emotion
import kotlinx.coroutines.flow.Flow

@Dao
interface EmotionDao {
    @Query("SELECT * From moods")
    fun getAllEmotions() : Flow<List<Emotion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmotion(emotion: Emotion)

    @Delete
    suspend fun deleteEmotion(emotion: Emotion)

    @Update
    suspend fun updateEmotion(emotion: Emotion)

    @Query("DELETE FROM moods")
    suspend fun clearAllEmotions() : Unit
}