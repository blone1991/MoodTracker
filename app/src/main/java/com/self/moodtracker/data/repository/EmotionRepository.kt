package com.self.moodtracker.data.repository

import com.self.moodtracker.data.local.EmotionDao
import com.self.moodtracker.data.model.Emotion
import com.self.moodtracker.ui.home.EmotionButton
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

class EmotionRepository @Inject constructor(
    private val emotionDao: EmotionDao
){
    val emotions : Flow<List<Emotion>> = emotionDao.getAllEmotions()

    suspend fun saveEmotion (emotionButton: EmotionButton) {
        val todayEmotion = Emotion (
            date = Date(System.currentTimeMillis()).toString(),
//            date = LocalDate.now().toString(),
            value = emotionButton.value,
            emotion = emotionButton.emotion,
            emoji = emotionButton.emoji
        )
        emotionDao.insertEmotion(todayEmotion);
    }

    suspend fun clearAllEmotions () {
        emotionDao.clearAllEmotions()
    }
}