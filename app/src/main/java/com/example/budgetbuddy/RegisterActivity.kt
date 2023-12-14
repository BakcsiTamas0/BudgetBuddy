package com.example.budgetbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.budgetbuddy.PasswordHashUtil.Companion.hashPassword
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var username : EditText
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var confirmPassword : EditText

    private lateinit var registerButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        username = findViewById(R.id.register_username)
        email = findViewById(R.id.register_email_address)
        password = findViewById(R.id.register_password)
        confirmPassword = findViewById(R.id.register_password_confirm)

        registerButton = findViewById(R.id.register_button)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.228:65432/")
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