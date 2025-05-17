package com.dicoding.asclepius.prediction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.asclepius.database.AsclepiusRoomDatabase
import com.dicoding.asclepius.database.PredictionDao

class PredictionLogViewModel (application: Application) : AndroidViewModel(application) {
    private val predictionDao: PredictionDao = AsclepiusRoomDatabase.getDatabase(application).predictionDao()

    fun getAllPredictions(): LiveData<List<PredictionLog>> {
        return predictionDao.getAllPredictions()
    }
}