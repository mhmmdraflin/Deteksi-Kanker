package com.dicoding.asclepius.retrofit

import com.dicoding.asclepius.response.ArticleResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/top-headlines")
    fun getArticles(
        @Query("q") query: String,
        @Query("category") category: String,
        @Query("language") language: String,
        @Query("apiKey") apiKey: String
    ): Call<ArticleResponse>  // Mengubah tipe return menjadi Call<ArticleResponse>
}