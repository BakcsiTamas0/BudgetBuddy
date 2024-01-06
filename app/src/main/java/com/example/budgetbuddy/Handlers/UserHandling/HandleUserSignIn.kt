package com.example.budgetbuddy.Handlers.UserHandling

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.budgetbuddy.Handlers.GoogleHandler.GoogleUserSave
import com.example.budgetbuddy.MainActivity
import com.example.budgetbuddy.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class HandleUserSignIn : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 7007

    private lateinit var signInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handle_user_sign_in)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInButton = findViewById<Button>(R.id.googleSignInBtn)

        signInLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            handleSignInResult(result.resultCode, result.data)
        }

        signInButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            signInLauncher.launch(signInIntent)
        }
    }

    private fun handleSignInResult(resultCode: Int, data: Intent?) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                updateUI(account)
            } else {
                // Sign-in failed
            }
        } catch (e: ApiException) {
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            val email = account.email.toString()
            val displayName = account.displayName.toString()

            if (!isUserRegistered(email)) {
                val googleUserSave = GoogleUserSave()
                googleUserSave.saveGoogleUser(displayName, email)

                setRegisteredUser(email)
            }

            val mainIntent = Intent(this@HandleUserSignIn, MainActivity::class.java)
            mainIntent.putExtra("USERNAME", displayName)
            startActivity(mainIntent)
            finish()
        } else {
            // Sign-in failed
            // You can handle failure here
        }
    }

    private fun isUserRegistered(email: String): Boolean {
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserRegistration", MODE_PRIVATE)
        return sharedPreferences.getBoolean(email, false)
    }

    private fun setRegisteredUser(email: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserRegistration", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(email, true)
        editor.apply()
    }
}
