package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.dicoding.asclepius.article.activity_article
import com.dicoding.asclepius.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.articleButton.setOnClickListener {
            moveToArticle()
        }
        binding.BtnLog.setOnClickListener {
            val intent = Intent(this, Log::class.java)
            startActivity(intent)
        }

        binding.galleryButton.setOnClickListener {
            startGallery()
        }

        binding.analyzeButton.setOnClickListener {
            analyzeImage()
        }
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                showImage(uri)
                currentImageUri = uri
            } else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            }
        }

    private fun startGallery() {
        // TODO: Mendapatkan gambar dari Gallery.
        galleryLauncher.launch("image/*")
    }

    private fun showImage(uri: Uri) {
        // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
        binding.previewImageView.setImageURI(uri)
    }

    private fun analyzeImage() {
        if (currentImageUri != null) {
            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra("imageUri", currentImageUri.toString())
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please Select an Image First", Toast.LENGTH_SHORT).show()
        }
    }

    private fun moveToResult() {
        val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun moveToArticle() {
        val intent = Intent(this, activity_article::class.java)
        startActivity(intent)
    }
}