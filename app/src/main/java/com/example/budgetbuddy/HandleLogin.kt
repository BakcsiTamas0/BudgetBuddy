package com.example.budgetbuddy

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.budgetbuddy.PasswordHashUtil.Companion.hashPassword
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HandleLogin {

    interface AuthCallback {
        fun onAuthSuccess()
        fun onAuthError()
    }
    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.228:65432/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val apiService = retrofit.create(ApiServices::class.java)

        fun authenticateUser(username: String, password: String, callback: AuthCallback) {
            val hashedPassword = hashPassword(password)
            val userLoginData = UserLoginDataClass(username, hashedPassword)

            val call: Call<Void> = apiService.authenticateUser(userLoginData)

            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        callback.onAuthSuccess()
                    } else {
                        callback.onAuthError()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("Error", "Login failed. ${t.message}", t)

                }
            })
        }
    }
}
