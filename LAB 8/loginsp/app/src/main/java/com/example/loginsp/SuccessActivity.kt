package com.example.loginsp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SuccessActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val usernameTextView: TextView = findViewById(R.id.textview)
        val deleteButton: Button = findViewById(R.id.deleteButton)

        sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")

        deleteButton.setOnClickListener {
            clearSharedPreferences()
        }

        if (username.isNullOrEmpty()) {
            usernameTextView.text = "Welcome, Guest!"
        } else {
            usernameTextView.text = "Welcome, $username!"
        }
    }

    private fun clearSharedPreferences() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}