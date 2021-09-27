package com.example.youtubeplayer

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class YoutubeVideoAdapter(private val context: Context, private val videos: List<YoutubeVideo>): RecyclerView.Adapter<YoutubeVideoAdapter.YoutubeVideoHolder>() {

    inner class YoutubeVideoHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        val videoTitle = itemView?.findViewById<TextView>(R.id.videoTitleTextView)
        val vidThumbnail = itemView?.findViewById<ImageView>(R.id.thumbnailImageView)
        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeVideoHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.lyt_ytvideo, parent, false)
        return YoutubeVideoHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: YoutubeVideoHolder, position: Int) {
        val currentVideo = videos[position]
        holder?.videoTitle.text = currentVideo.snippet.title
        Glide.with(holder?.vidThumbnail.context).load(currentVideo.snippet.thumbnails.high.url).into(holder?.vidThumbnail)
    }

    override fun getItemCount(): Int {
        return videos.size
    }
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }
}