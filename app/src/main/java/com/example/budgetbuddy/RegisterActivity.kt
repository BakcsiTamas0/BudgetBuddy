package com.example.budgetbuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.budgetbuddy.Handlers.UserHandling.HandleRegister
import com.example.budgetbuddy.Utils.CustomTextUtils
import com.example.budgetbuddy.Utils.PasswordHashUtil.Companion.hashPassword
import com.example.budgetbuddy.Utils.RegisterInformationUtils
import com.google.firebase.messaging.FirebaseMessaging

class RegisterActivity : AppCompatActivity() {
    private lateinit var customTextUtils: CustomTextUtils

    private lateinit var signUp: TextView

    private lateinit var username : EditText
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var confirmPassword : EditText
    private lateinit var registerButton : Button
    private lateinit var loginFromRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val handleRegister = HandleRegister(this)
        val registerUtils = RegisterInformationUtils()

        customTextUtils = CustomTextUtils()

        signUp = findViewById(R.id.signUp)
        customTextUtils.applyLinearGradient(signUp)

        username = findViewById(R.id.register_username)
        email = findViewById(R.id.register_email_address)

        password = findViewById(R.id.register_password)
        confirmPassword = findViewById(R.id.register_password_confirm)

        registerButton = findViewById(R.id.register_button)

        loginFromRegister = findViewById(R.id.loginFromRegister)
        customTextUtils.applyLinearGradient(loginFromRegister)

        loginFromRegister.setOnClickListener() {
            val login = Intent(this, LoginActivity::class.java)
            startActivity(login)
        }

        if (password.text.toString() == confirmPassword.text.toString()) {
            registerButton.setOnClickListener() {

                val passwordString = password.text.toString()
                val confirmPasswordString = confirmPassword.text.toString()
                val hashedPassword = hashPassword(passwordString)

                val emailAddressString = email.text.toString()

                val username = username.text.toString()

                val checkedPassword = registerUtils.checkIfValidPassword(passwordString, confirmPasswordString)
                val checkedEmailAddress = registerUtils.checkIfValidEmailAddress(emailAddressString)

                getFirebaseMessagingToken{ messageToken ->

                    if (checkedPassword && checkedEmailAddress) {
                        handleRegister.registerUser(
                            username,
                            emailAddressString,
                            hashedPassword,
                            messageToken
                        )
                    } else {
                        Toast.makeText(this, "Invalid Password or Email", Toast.LENGTH_SHORT).show()
                    }

                }

            }
        }
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