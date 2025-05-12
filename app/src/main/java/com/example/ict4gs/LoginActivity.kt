package com.example.ict4gs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ict4gs.ui.theme.ICT4GSTheme
import androidx.core.content.edit

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ICT4GSTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        onLoginSuccess = {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        },
                        getSharedPreferences("UserCredentials", Context.MODE_PRIVATE)
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, sharedPref: android.content.SharedPreferences) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.mipmap.heart_foreground),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(240.dp)
                .padding(bottom = 1.dp)
        )
        Text("Autism Support App", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Please log in or register", fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),

        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (username.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "Please enter username and password", Toast.LENGTH_SHORT).show()
                } else {
                    val savedPassword = sharedPref.getString(username, null)
                    if (savedPassword == null) {
                        Toast.makeText(context, "User not found. Please register first.", Toast.LENGTH_SHORT).show()
                    } else if (savedPassword == password) {
                        sharedPref.edit() { putString("loggedInUser", username) }
                        Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                        onLoginSuccess()
                    } else {
                        Toast.makeText(context, "Incorrect password.", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                if (username.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "Please enter username and password", Toast.LENGTH_SHORT).show()
                } else {
                    if (sharedPref.contains(username)) {
                        Toast.makeText(context, "Username already exists. Please choose another.", Toast.LENGTH_SHORT).show()
                    } else {
                        sharedPref.edit() { putString(username, password) }
                        Toast.makeText(context, "Registration successful! You can now login.", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Register")
        }
    }
}
