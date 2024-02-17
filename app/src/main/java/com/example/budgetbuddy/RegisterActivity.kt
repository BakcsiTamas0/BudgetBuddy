package com.example.budgetbuddy

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextPaint
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.budgetbuddy.API.ApiServices
import com.example.budgetbuddy.DataClasses.UserData.UserRegisterDataClass
import com.example.budgetbuddy.Utils.CustomTextUtils
import com.example.budgetbuddy.Utils.PasswordHashUtil.Companion.hashPassword
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        val retrofit = Retrofit.Builder()
            .baseUrl("https://bakcsitamas.pythonanywhere.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiServices::class.java)

        if (password.text.toString() == confirmPassword.text.toString()) {
            registerButton.setOnClickListener() {

                val newUser = UserRegisterDataClass(
                    username = username.text.toString(),
                    password = hashPassword(password.text.toString()),
                    email = email.text.toString()
                )

                val call = apiService.registerUser(newUser)

                call.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                        if (response.isSuccessful) {
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("Error", t.message.toString())
                    }
                })

            }
        }
    }
}