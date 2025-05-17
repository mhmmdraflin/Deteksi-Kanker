package com.dicoding.asclepius.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dicoding.asclepius.prediction.PredictionLog

@Dao
interface PredictionDao {

    @Insert
    suspend fun insertPrediction(PredictionLog: PredictionLog)

    @Update
    suspend fun updatePrediction(PredictionLog: PredictionLog)

    @Query("SELECT * FROM Prediction_Log WHERE imageUri = :imageUri AND result = :result LIMIT 1")
    suspend fun getPredictionByUriAndResult(imageUri: String, result: String): PredictionLog?

    @Query("SELECT * FROM Prediction_Log")
    fun getAllPredictions(): LiveData<List<PredictionLog>>
}
