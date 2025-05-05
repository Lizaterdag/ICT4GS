package com.example.ict4gs

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ict4gs.databinding.ActivityMainBinding
import com.example.ict4gs.ui.chat.ChatFragment
import com.example.ict4gs.ui.home.HomeFragment
import com.example.ict4gs.ui.profile.ProfileFragment
import com.example.ict4gs.ui.training.TrainingFragment
import com.example.ict4gs.utils.LocaleHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun attachBaseContext(newBase: Context) {
        val sharedPref = newBase.getSharedPreferences("UserCredentials", Context.MODE_PRIVATE)
        val savedLanguage = sharedPref.getString("language", "English")
        val context = LocaleHelper.setLocale(newBase, savedLanguage!!)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        loadFragment(HomeFragment())

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_chat -> loadFragment(ChatFragment())
                R.id.nav_training -> loadFragment(TrainingFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
