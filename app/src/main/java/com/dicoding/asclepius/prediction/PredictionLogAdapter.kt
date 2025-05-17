package com.dicoding.asclepius.prediction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ItemPredictionLogBinding

class PredictionLogAdapter: ListAdapter<PredictionLog, PredictionLogAdapter.PredictionLogViewHolder>(PredictionLogDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionLogViewHolder {
        val binding = ItemPredictionLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PredictionLogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PredictionLogViewHolder, position: Int) {
        val predictionLog = getItem(position)
        holder.bind(predictionLog)
    }

    inner class PredictionLogViewHolder(private val binding: ItemPredictionLogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(PredictionLog: PredictionLog) {
            binding.ResultPrediction.text = PredictionLog.result
            binding.ScoreConfidence.text ="Confidence: ${String.format("%.0f", PredictionLog.ScoreConfidence * 100)}%"
            Glide.with(binding.root.context)
                .load(PredictionLog.imageUri)
                .placeholder(R.drawable.ic_place_holder)
                .into(binding.ImagePrediction)

        }
    }

    class PredictionLogDiffCallback : DiffUtil.ItemCallback<PredictionLog>() {
        override fun areItemsTheSame(oldItem: PredictionLog, newItem: PredictionLog): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PredictionLog, newItem: PredictionLog): Boolean {
            return oldItem == newItem
        }
    }
}

