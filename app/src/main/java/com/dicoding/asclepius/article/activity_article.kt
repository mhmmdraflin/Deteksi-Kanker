package com.dicoding.asclepius.article

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityArticleBinding
import com.dicoding.asclepius.databinding.ActivityDetailArticleBinding
import com.dicoding.asclepius.response.ArticleResponse
import com.dicoding.asclepius.response.ArticlesItem
import com.dicoding.asclepius.retrofit.ApiConfig
import com.dicoding.asclepius.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class activity_article : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding
    private val AdapterArticle = ArticleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
        binding.RecyclerView.adapter = AdapterArticle

        retrieveArticles()

        val ButtonBack = findViewById<ImageButton>(R.id.BtnBack)

        ButtonBack.setOnClickListener {
            val intent = Intent(this@activity_article, MainActivity::class.java)
            startActivity(intent)

            finish()
        }
    }

    private fun retrieveArticles() {
        binding.progressBar.visibility = View.VISIBLE

        val apiService = ApiConfig.getApiService()
        apiService.getArticles("cancer", "health", "en", "961d70aed67d46fb87f267536ff8c85b")
            .enqueue(object : Callback<ArticleResponse> {
                override fun onResponse(
                    call: Call<ArticleResponse>,
                    response: Response<ArticleResponse>
                ) {
                    binding.progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        val articles = response.body()?.articles?.filterNotNull() ?: emptyList()
                        val validArticles = articles.filter { isArticleValid(it) }
                        AdapterArticle.submitList(validArticles)
                    } else {
                        Toast.makeText(
                            this@activity_article,
                            "Gagal mengambil data: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@activity_article, "Error: ${t.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    private fun isArticleValid(article: ArticlesItem): Boolean {
        return article.title != "[Removed]" && article.description != "[Removed]" && article.author != null
                && article.urlToImage != null && article.url != "[Removed]"
    }
}