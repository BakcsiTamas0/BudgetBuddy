package com.example.budgetbuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    private val PREFS_NAME = "preferences"
    private val PREF_USERNAME = "username"
    private val PREF_PASSWORD = "password"
    private val PREF_REMEMBER = "remember"

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var remember: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Move the initialization here
        username = findViewById(R.id.login_username)
        password = findViewById(R.id.login_password)
        remember = findViewById(R.id.remember)

        val loginBtn: Button = findViewById(R.id.login_btn)
        val registerFromLogin: TextView = findViewById(R.id.register_from_login)

        loginBtn.setOnClickListener {
            Log.d("LoginActivity", username.text.toString())
            Log.d("LoginActivity", password.text.toString())

            if (remember.isChecked) {
                savePreferences()
            } else {
                clearPreferences()
            }

            if (username.text.toString() != "" && password.text.toString() != "") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        registerFromLogin.setOnClickListener {
            val register = Intent(this, RegisterActivity::class.java)
            startActivity(register)
        }

        loadPreferences()
    }

    override fun onResume() {
        super.onResume()
        loadPreferences()
    }

    private fun savePreferences() {
        val preferences = getSharedPreferences(PREFS_NAME, 0)
        val editor = preferences.edit()
        editor.putString(PREF_USERNAME, username.text.toString())
        editor.putString(PREF_PASSWORD, password.text.toString())
        editor.putBoolean(PREF_REMEMBER, true)
        editor.apply()
    }

    private fun clearPreferences() {
        val preferences = getSharedPreferences(PREFS_NAME, 0)
        val editor = preferences.edit()
        editor.remove(PREF_USERNAME)
        editor.remove(PREF_PASSWORD)
        editor.putBoolean(PREF_REMEMBER, false)
        editor.apply()
    }

    private fun loadPreferences() {
        val preferences = getSharedPreferences(PREFS_NAME, 0)
        val usernameValue = preferences.getString(PREF_USERNAME, "")
        val passwordValue = preferences.getString(PREF_PASSWORD, "")
        val rememberValue = preferences.getBoolean(PREF_REMEMBER, false)

        username.setText(usernameValue)
        password.setText(passwordValue)
        remember.isChecked = rememberValue
    }
}
