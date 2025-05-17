package com.dicoding.asclepius.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityLogBinding
import com.dicoding.asclepius.prediction.PredictionLogAdapter
import com.dicoding.asclepius.prediction.PredictionLogViewModel

class Log : AppCompatActivity() {
    private lateinit var binding: ActivityLogBinding
    private lateinit var logAdapter: PredictionLogAdapter
    private lateinit var predictionLogViewModel: PredictionLogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        logAdapter = PredictionLogAdapter()
        binding.RecyclerViewLog.adapter = logAdapter
        binding.RecyclerViewLog.layoutManager = LinearLayoutManager(this)

        predictionLogViewModel = ViewModelProvider(this).get(PredictionLogViewModel::class.java)

        predictionLogViewModel.getAllPredictions().observe(this) { predictions ->
            predictions?.let {
                Log.d("LogActivity", "Predictions: $it")
                if (it.isEmpty()) {
                    binding.RecyclerViewLog.visibility = View.GONE
                    binding.MessageTextEmpty.visibility = View.VISIBLE
                } else {
                    binding.RecyclerViewLog.visibility = View.VISIBLE
                    binding.MessageTextEmpty.visibility = View.GONE
                }
                logAdapter.submitList(it)
                logAdapter.notifyDataSetChanged()
            }
        }

        binding.BtnBack.setOnClickListener {
            onBackPressed()
        }
    }
}