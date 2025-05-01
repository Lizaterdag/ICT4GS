package com.example.ict4gs.ui.training

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ict4gs.R

class AutismInfoActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autism_info)

        // default back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // handle back button press
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}