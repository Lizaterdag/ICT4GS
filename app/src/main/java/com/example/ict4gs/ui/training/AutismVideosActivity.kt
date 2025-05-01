package com.example.ict4gs.ui.training

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ict4gs.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class AutismVideosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autism_videos)
        // default back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val youtubePlayerView1 = findViewById<YouTubePlayerView>(R.id.youtubePlayerView1)
        val youtubePlayerView2 = findViewById<YouTubePlayerView>(R.id.youtubePlayerView2)
        val youtubePlayerView3 = findViewById<YouTubePlayerView>(R.id.youtubePlayerView3)

        lifecycle.addObserver(youtubePlayerView1)
        lifecycle.addObserver(youtubePlayerView2)
        lifecycle.addObserver(youtubePlayerView3)

        youtubePlayerView1.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo("TJuwhCIQQTs", 0f)
            }
        })

        youtubePlayerView2.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo("WZrp44FWTOk", 0f)
            }
        })

        youtubePlayerView3.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo("TYgfoWuwlvQ", 0f)
            }
        })

    }
    // handle back button press
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
