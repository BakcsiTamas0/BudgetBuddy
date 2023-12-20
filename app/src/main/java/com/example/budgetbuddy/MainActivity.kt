package com.example.budgetbuddy

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var executor: Executor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout: androidx.drawerlayout.widget.DrawerLayout = findViewById(R.id.drawerLayout)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val drawerusername = intent.getStringExtra("USERNAME")

        getEmailByUsername(drawerusername.toString())

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setOnClickListener() {
            Log.d("TAG", "clicked")
        }

        //initBiometric()
        //showBiometricPrompt()
    }

    private fun getEmailByUsername(username: String) {
        val intentUsername = intent.getStringExtra("USERNAME")
        Log.d("username", intentUsername.toString())
        val drawerusername: TextView = findViewById(R.id.drawerUsername)
        drawerusername.text = intentUsername

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.228:65432/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiServices = retrofit.create(ApiServices::class.java)

        val call = apiServices.getEmailByUsername(username)

        call.enqueue(object : Callback<EmailResponse> {
            override fun onResponse(call: Call<EmailResponse>, response: Response<EmailResponse>) {
                if (response.isSuccessful) {
                    val email: String = response.body()!!.email
                    if (email != null) {
                        updateDrawerViews(username, email)
                    } else {
                        // Handle empty or null email
                    }
                } else {
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<EmailResponse>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }

            private fun updateDrawerViews(username: String, email: String) {
                val usernameTextView: TextView = findViewById(R.id.drawerUsername)
                val emailTextView: TextView = findViewById(R.id.drawerEmail)

                usernameTextView.text = username
                emailTextView.text = email
            }
        })
    }


    // Biometric authentication setup
    private fun initBiometric() {
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this, executor, biometricCallback)

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Use your fingerprint or face to continue")
            .setNegativeButtonText("Cancel")
            .build()
    }

    private fun showBiometricPrompt() {
        biometricPrompt.authenticate(promptInfo)
    }

    private val biometricCallback = object : BiometricPrompt.AuthenticationCallback() {

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            Log.e("TAG", "Authentication error: $errString")
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            Log.d("TAG", "Authentication succeeded!")
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            Log.d("TAG", "Authentication failed!")
        }
    }
}
