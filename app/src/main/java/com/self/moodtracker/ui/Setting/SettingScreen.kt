package com.self.moodtracker.ui.Setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.room.PrimaryKey

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
) {
    Column (
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Settings", fontWeight = FontWeight.Bold)
        Button(onClick = { viewModel.clearEmotionHistory()}) {
            Text("Clear All Moods")
        }

    }
}