package com.example.budgetbuddy.Handlers.UserHandling

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
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
import com.google.firebase.messaging.FirebaseMessaging

class HandleUserSignIn : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var signInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handle_user_sign_in)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
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
                Toast.makeText(this,"Sign-in failed", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            Log.e("SignInError", "Error code: ${e.statusCode}, message: ${e.message}", e)
            updateUI(null)
        }
    }


    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            val email = account.email.toString()
            val displayName = account.displayName.toString()
            val accountTokenID = account.idToken.toString()

            if (!isUserRegistered(email)) {
                val googleUserSave = GoogleUserSave()

                getFirebaseMessagingToken { messageToken ->
                    googleUserSave.saveGoogleUser(displayName, email, accountTokenID, messageToken)
                }

                setRegisteredUser(email)
            }

            val mainIntent = Intent(this@HandleUserSignIn, MainActivity::class.java)
            mainIntent.putExtra("USERNAME", displayName)
            startActivity(mainIntent)
            finish()
        } else {
            Toast.makeText(this,"Sign-in failed", Toast.LENGTH_SHORT).show()
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

    private fun getFirebaseMessagingToken(callback: (String) -> Unit) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    callback.invoke(token)
                } else {
                    callback.invoke("")
                }
            }
    }
}
