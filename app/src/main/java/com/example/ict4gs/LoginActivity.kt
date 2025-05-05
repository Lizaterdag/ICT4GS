package com.example.ict4gs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ict4gs.databinding.ActivityLoginBinding
import androidx.core.content.edit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("UserCredentials", Context.MODE_PRIVATE)

        binding.loginButton.setOnClickListener {
            val username = binding.usernameInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val savedPassword = sharedPref.getString(username, null)

            if (savedPassword == null) {
                Toast.makeText(this, "User not found. Please register first.", Toast.LENGTH_SHORT).show()
            } else if (savedPassword == password) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                sharedPref.edit {
                    putString("loggedInUser", username)
                }
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Incorrect password.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerButton.setOnClickListener {
            val username = binding.usernameInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (sharedPref.contains(username)) {
                Toast.makeText(this, "Username already exists. Please choose another.", Toast.LENGTH_SHORT).show()
            } else {
                sharedPref.edit().putString(username, password).apply()
                Toast.makeText(this, "Registration successful! You can now login.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
