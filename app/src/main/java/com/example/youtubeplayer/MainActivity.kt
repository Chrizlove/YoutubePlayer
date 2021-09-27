package com.example.youtubeplayer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.youtube.player.YouTubeApiServiceUtil
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var youtubeadapter: YoutubeVideoAdapter
    private lateinit var videosList: List<YoutubeVideo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //code for entire searchview to be clivkable
        searchbar.setOnClickListener {
            searchbar.isIconified = false
        }

        //showing hint when searchview is tapped on
        searchbar.queryHint = "Search for videos"

        //code for onsubmit of searchview query
        searchbar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(keyword: String?): Boolean {

                //clearing search bar text

                //fetch list of videos
                keyword?.let {
                    getVideosList(it)
                }
                //hiding keyboard when submit button is pressed
                val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun setUpRecyclerView(videos: List<YoutubeVideo>) {
        youtubeadapter = YoutubeVideoAdapter(this, videos)
        val layoutManager =  LinearLayoutManager(this)
        ytVideosRecycler.layoutManager= layoutManager
        ytVideosRecycler.adapter = youtubeadapter

        //listener for video click and to change activity to play the video

        youtubeadapter.setOnItemClickListener(object: YoutubeVideoAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(applicationContext, YoutubeVideoPlayerActivity::class.java)
                intent.putExtra("title",videos[position].snippet.title)
                intent.putExtra("desc",videos[position].snippet.description)
                intent.putExtra("videoId",videos[position].id.videoId)
                startActivity(intent)
            }
        })
    }

    private fun getVideosList(keyword: String) {
        val videos = RetrofitInstance.youtubeapi.getYoutubeVideos("snippet",keyword,"AIzaSyD0eAZjbaucIZYrc5XfdXwe2iWyXNrw5Wk",10,"video")
        videos.enqueue(object : Callback<YoutubeAPIData?> {
            override fun onResponse(call: Call<YoutubeAPIData?>, response: Response<YoutubeAPIData?>) {
                 videosList = response.body()?.items!!
                //setting up recycler view
                videosList?.let { setUpRecyclerView(it) }
            }
            override fun onFailure(call: Call<YoutubeAPIData?>, t: Throwable) {
                Toast.makeText(applicationContext, "Unable to fetch results!", Toast.LENGTH_SHORT).show()
                Log.d("APIError",t.toString())
            }
        })
    }
}