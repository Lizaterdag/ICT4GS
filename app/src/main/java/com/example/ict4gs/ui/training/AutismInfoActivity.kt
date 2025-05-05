package com.example.ict4gs.ui.training

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ict4gs.R

class AutismInfoActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        val sharedPref = newBase.getSharedPreferences("UserCredentials", Context.MODE_PRIVATE)
        val savedLanguage = sharedPref.getString("language", "English")
        val context = com.example.ict4gs.utils.LocaleHelper.setLocale(newBase, savedLanguage!!)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autism_info)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.autism_info_title)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
