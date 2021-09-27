package com.example.youtubeplayer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface YoutubeListApi {
    @GET("search")
    fun getYoutubeVideos(@Query ("part") part: String,
                         @Query(value="q") keyword: String,
                         @Query(value="key") key: String,
                         @Query("maxResults") max: Int,
                         @Query("type") type: String): Call<YoutubeAPIData>
}