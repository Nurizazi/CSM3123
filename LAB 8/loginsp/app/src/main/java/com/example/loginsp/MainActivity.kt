package com.example.loginsp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val sharedPrefKey = "login_preference"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE)

        val usernameEditText: EditText = findViewById(R.id.usernameEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val loginButton: Button = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (isValidCredentials(username, password)) {
                saveCredentials(username, password)
                openSuccessPage(username)
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }

        if (isUserLoggedIn()) {
            val username = sharedPreferences.getString("username", "")
            if (username != null) {
                openSuccessPage(username)
            }
        }
    }

    private fun isValidCredentials(username: String, password: String): Boolean {
        return username.isNotBlank() && password.isNotBlank()
    }

    private fun saveCredentials(username: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }

    private fun isUserLoggedIn(): Boolean {
        val username = sharedPreferences.getString("username", "")
        val password = sharedPreferences.getString("password", "")
        return username?.isNotBlank() == true && password?.isNotBlank() == true
    }

    private fun openSuccessPage(username: String) {
        val intent = if (username.isNotBlank()) {
            Intent(this, SuccessActivity::class.java).apply {
                putExtra("username", username)
            }
        } else {
            Intent(this, MainActivity::class.java)
        }
        startActivity(intent)
        finish()
    }

}
