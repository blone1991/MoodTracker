package com.self.moodtracker.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.self.moodtracker.data.model.Emotion
import com.self.moodtracker.data.model.Quote


data class EmotionButton (val value : Int, val emotion : String, val emoji : String)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val emotions = viewModel.emotions.collectAsState()
    val quote = viewModel.quote.collectAsState()
    val aiSupportMessage = viewModel.aiSupportMessage.collectAsState()

    Column {
        SelectTodayEmotionComposable(onEmotionSelected = { emotionButton ->
            viewModel.saveTodayEmotion(emotionButton)
        })
        EmotionalSupportComposable(aiSupportMessage = aiSupportMessage.value)
        DisplayTodaysQuoteComposable(quote = quote.value)
        EmotionHistoryComposable(emotions = emotions.value)
    }
}

@Composable
fun SelectTodayEmotionComposable(onEmotionSelected: (EmotionButton) -> Unit) {
    val emotionButtons = listOf(
        EmotionButton(2, "great", "😍"),
        EmotionButton(1, "good", "😄"),
        EmotionButton(0, "so so", "🙂"),
        EmotionButton(-1, "not good", "☹️"),
        EmotionButton(-2, "sad", "😭"),
    )
    Column {
        Text("How do you feel today?", fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            emotionButtons.forEach { button ->
                Button(
                    contentPadding = PaddingValues(horizontal = 5.dp, vertical = 0.dp),
                    onClick = { onEmotionSelected(button) }
                ) {
                    Text("${button.emoji} ${button.emotion}")
                }
            }
        }
    }
}

@Composable
fun EmotionalSupportComposable(aiSupportMessage: String) {
    Column(
        modifier = Modifier
            .defaultMinSize(minHeight = 80.dp)
            .padding(vertical = 15.dp)
    ) {
        Text("Words for you", fontWeight = FontWeight.Bold)
        if (aiSupportMessage.isNotEmpty()) {
            Text(aiSupportMessage)
        }
    }
}

@Composable
fun DisplayTodaysQuoteComposable(quote: Quote) {
    Column(
        modifier = Modifier
            .defaultMinSize(minHeight = 80.dp)
            .padding(vertical = 15.dp)
    ) {
        if (quote.message.isNotEmpty()) {
            Text(text = "Today's Quote", fontWeight = FontWeight.Bold)
            Text("${quote.message} - ${quote.author ?: "Unknown"}")
        }
    }
}

@Composable
fun EmotionHistoryComposable(emotions: List<Emotion>) {
    Column {
        Text(text = "Recent Moods", fontWeight = FontWeight.Bold)
        emotions.sortedBy { it.date }.take(7).forEach {
            Text("- ${it.emotion} - ${it.date}")
        }
    }
}