package com.dicoding.asclepius.view

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.RoomDatabase
import com.dicoding.asclepius.R
import com.dicoding.asclepius.database.AsclepiusRoomDatabase
import com.dicoding.asclepius.database.PredictionDao
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.prediction.PredictionLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.task.vision.classifier.Classifications

class ResultActivity : AppCompatActivity(), ImageClassifierHelper.ClassifierListener {
    private lateinit var binding: ActivityResultBinding
    private var imageUri: Uri? = null
    private lateinit var database: AsclepiusRoomDatabase
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AsclepiusRoomDatabase.getDatabase(applicationContext)

        imageClassifierHelper = ImageClassifierHelper(
            context = applicationContext,
            threshold = 0.1f,
            maxResults = 3,
            listener = this // Menggunakan ResultActivity sebagai listener
        )

        val imageUriString = intent.getStringExtra("imageUri")
        if (imageUriString.isNullOrEmpty()) {
            Toast.makeText(this, "No image URI received", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        imageUri = Uri.parse(imageUriString)

        if (imageUri != null) {
            binding.resultImage.setImageURI(imageUri)
            processImage(imageUri!!)
        } else {
            Toast.makeText(this, "Error: No image available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun processImage(uri: Uri) {
        val bitmap = getBitmapFromUri(uri)
        if (bitmap != null) {
            imageClassifierHelper.classifyStaticImage(bitmap)
        } else {
            Toast.makeText(this, "Error processing image", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResults(results: List<Classifications>?, confidenceScore: Float) {
        val resultString = results?.get(0)?.categories?.get(0)?.label ?: "Unknown"
        val confidencePercentage = (confidenceScore * 100).toInt()

        binding.resultText.text = "Prediction: $resultString\nConfidence Score: $confidencePercentage%"
        savePredictionToDatabase(resultString, confidenceScore)
    }

    override fun onError(error: String) {
        Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
    }

    private fun savePredictionToDatabase(result: String, confidenceScore: Float) {
        lifecycleScope.launch(Dispatchers.IO) {
            val prediction = PredictionLog(
                imageUri = imageUri.toString(),
                result = result,
                ScoreConfidence = confidenceScore
            )

            val existingPrediction = database.predictionDao().getPredictionByUriAndResult(imageUri.toString(), result)

            if (existingPrediction == null) {
                database.predictionDao().insertPrediction(prediction)
                Log.d("ResultActivity", "Prediction saved: $prediction")
            } else {
                val updatedPrediction = existingPrediction.copy(ScoreConfidence = confidenceScore)
                database.predictionDao().updatePrediction(updatedPrediction)
                Log.d("ResultActivity", "Prediction updated: $updatedPrediction")
            }
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        } catch (e: Exception) {
            null
        }
    }
}