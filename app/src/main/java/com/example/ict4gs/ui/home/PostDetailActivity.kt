package com.example.ict4gs.ui.home

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ict4gs.R

class PostDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")

        val titleText: TextView = findViewById(R.id.postTitle)
        val contentText: TextView = findViewById(R.id.postContent)

        titleText.text = title
        contentText.text = content

        // Enable the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}