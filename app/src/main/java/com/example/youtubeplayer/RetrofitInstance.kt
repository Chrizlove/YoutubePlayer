package com.example.youtubeplayer

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    const val BASE_URL = "https://www.googleapis.com/youtube/v3/"
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val youtubeapi: YoutubeListApi by lazy {
        retrofit.create(YoutubeListApi::class.java)
    }
}