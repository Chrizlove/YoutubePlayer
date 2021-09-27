package com.example.youtubeplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_youtube_video_player.*

class YoutubeVideoPlayerActivity : YouTubeBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_video_player)

        //recieving intents
        val videotitle:String = intent.getStringExtra("title").toString()
        val videoDesc:String = intent.getStringExtra("desc").toString()
        val videoId:String = intent.getStringExtra("videoId").toString()
        Log.d("title",videotitle)

        //setting title and desc of videos
        titleText.text = videotitle
        descText.text = videoDesc
        //loading the player
        initalizePlayer(videoId)
    }

    private fun initalizePlayer(videoId: String) {
        youtubePlayerView.initialize(getString(R.string.youtube_api_key),object: YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                //playing video
                p1?.loadVideo(videoId)
                p1?.play()
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(applicationContext,"Something went wrong",Toast.LENGTH_LONG).show()
            }

        })
    }
}