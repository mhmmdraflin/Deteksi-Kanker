package com.dicoding.asclepius.article

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityDetailArticleBinding
import com.squareup.picasso.Picasso

class DetailArticle : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)

        val Buttonback = findViewById<ImageButton>(R.id.BtnBack)

        Buttonback.setOnClickListener {
            val intent = Intent(this@DetailArticle, activity_article::class.java)
            startActivity(intent)

            finish()
        }

        val Titlearticle = intent.getStringExtra("title")
        val Descriptionarticle = intent.getStringExtra("description")
        val Authorarticle = intent.getStringExtra("author")
        val Urlarticle = intent.getStringExtra("url")
        val ArticleImageUrl = intent.getStringExtra("imageUrl")

        findViewById<TextView>(R.id.Titlearticle).text = Titlearticle
        findViewById<TextView>(R.id.DescArticle).text = Descriptionarticle
        findViewById<TextView>(R.id.Authorarticle).text = Authorarticle
        findViewById<TextView>(R.id.Urlarticle).text = Urlarticle

        val ImageViewArticle: ImageView = findViewById(R.id.Imagearticle)
        if (!ArticleImageUrl.isNullOrEmpty()) {
            Picasso.get().load(ArticleImageUrl).into(ImageViewArticle)
        } else {
            ImageViewArticle.setImageResource(R.drawable.ic_place_holder)
        }
        val TextViewUrl = findViewById<TextView>(R.id.Urlarticle)
        TextViewUrl.text = Urlarticle
        TextViewUrl.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Urlarticle))
            startActivity(intent)
        }
    }
}
