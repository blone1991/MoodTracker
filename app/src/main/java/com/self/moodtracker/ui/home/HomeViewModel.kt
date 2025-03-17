package com.self.moodtracker.ui.home

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.self.moodtracker.BuildConfig
import com.self.moodtracker.UiState
import com.self.moodtracker.data.local.EmotionDao
import com.self.moodtracker.data.model.Emotion
import com.self.moodtracker.data.model.Quote
import com.self.moodtracker.data.network.QuoteApiService
import com.self.moodtracker.data.repository.EmotionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val emotionRepository: EmotionRepository,
    private val quoteApiService: QuoteApiService
) : ViewModel() {
    val emotions : StateFlow<List<Emotion>> = emotionRepository.emotions
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val _quote = MutableStateFlow(Quote("", null))
    val quote = _quote.asStateFlow()

    val _aiSupportMessage = MutableStateFlow("")
    val aiSupportMessage = _aiSupportMessage.asStateFlow()

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    fun saveTodayEmotion (emotionButton: EmotionButton) {
        viewModelScope.launch {
            fetchQuote()
            emotionRepository.saveEmotion(emotionButton)
            sendPrompt("My emotional state is ${emotionButton.emotion},\n" +
                    "Please give me some words of sympathy or comfort that can help my emotional state.\n" +
                    "\n" +
                    "I would like the sentence length to be at most 5 sentences, and the answer should not be the same every time.")
        }
    }

    private suspend fun fetchQuote() {
        try {
            val response = quoteApiService.getRandomQuote()
            if (response.isSuccessful) {
                _quote.value = response.body() ?: Quote("", null)
            }
        } catch (e: Exception) {
            Log.e("MoodViewModel", "Error fetching quote", e)
        }
    }

    // For Gemini AI
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    fun sendPrompt(
        prompt: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        text(prompt)
                    }
                )
                response.text?.let { outputContent ->
                    _aiSupportMessage.value = outputContent
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "")
            }
        }
    }
}