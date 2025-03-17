package com.self.moodtracker.ui.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.self.moodtracker.data.model.Emotion
import com.self.moodtracker.data.repository.EmotionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val emotionRepository: EmotionRepository
) : ViewModel() {

    private val _emotionsMap = MutableStateFlow<Map<String, Int>>(emptyMap())
    val emotionsMap: StateFlow<Map<String, Int>> = _emotionsMap.asStateFlow()

    val emotions: StateFlow<List<Emotion>> = emotionRepository.emotions
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // emotions가 변경될 때마다 실행되는 함수
    fun updateEmotionsMap() {
        viewModelScope.launch {
            _emotionsMap.value = emotions.value.groupingBy { it.emotion }.eachCount()
        }
    }

    init {
        // 초기 데이터 로딩 시 emotionsMap 업데이트
        updateEmotionsMap()

        viewModelScope.launch {
            // emotions가 변경될 때마다 emotionsMap 업데이트 (선택적)
            emotions.collectLatest { _ ->
                updateEmotionsMap()
            }
        }
    }
}