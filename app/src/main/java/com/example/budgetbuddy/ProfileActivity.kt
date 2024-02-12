package com.example.budgetbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileUsername: TextView
    private lateinit var profileEmail: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileUsername = findViewById(R.id.profileUsername)
        profileEmail = findViewById(R.id.profileEmailAddress)

        profileUsername.text = intent.getStringExtra("USERNAME")
        profileEmail.text = intent.getStringExtra("EMAIL")

    }
}