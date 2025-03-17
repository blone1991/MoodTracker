package com.self.moodtracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moods")
data class Emotion (
    @PrimaryKey
    val date : String,
    val value : Int,
    val emotion : String,
    val emoji : String
)