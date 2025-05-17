package com.dicoding.asclepius.prediction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Prediction_Log")
data class PredictionLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageUri: String,
    val result: String,
    val ScoreConfidence: Float
)