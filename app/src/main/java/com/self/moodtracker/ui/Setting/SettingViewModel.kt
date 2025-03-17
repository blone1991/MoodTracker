package com.self.moodtracker.ui.Setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.self.moodtracker.data.repository.EmotionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val repository: EmotionRepository
) : ViewModel() {
    fun clearEmotionHistory () {
        viewModelScope.launch {
            repository.clearAllEmotions()
        }
    }
}