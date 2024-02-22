package com.example.budgetbuddy.Handlers.UserHandling

import android.util.Log
import com.example.budgetbuddy.API.ApiServices
import com.example.budgetbuddy.Utils.PasswordHashUtil.Companion.hashPassword
import com.example.budgetbuddy.Utils.RetrofitUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HandleLogin {

    interface AuthCallback {
        fun onAuthSuccess()
        fun onAuthError()
    }

    companion object {
        val retrofit: Retrofit = RetrofitUtils.initRetrofit()
        private val apiService = retrofit.create(ApiServices::class.java)

        fun authenticateUser(username: String, password: String, callback: AuthCallback) {
            val hashedPassword = hashPassword(password)

            val call: Call<Void> = apiService.authenticateUser(username, hashedPassword)

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
