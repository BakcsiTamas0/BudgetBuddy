package com.example.budgetbuddy.Handlers.UserHandling

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.budgetbuddy.API.ApiServices
import com.example.budgetbuddy.LoginActivity
import com.example.budgetbuddy.Utils.RetrofitUtils
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HandleRegister(
    private val context: Context
) {
    private val retrofit: Retrofit = RetrofitUtils.initRetrofit()
    private val apiService = retrofit.create(ApiServices::class.java)

    fun registerUser(username: String, email: String, password: String, messageToken: String) {
        val call: Call<Void> = apiService.registerUser(username, password, email, messageToken)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()

                    val loginIntent = Intent(context, LoginActivity::class.java)
                    loginIntent.putExtra("registerUsername", username)
                    context.startActivity(loginIntent)

                } else  {
                    if (response.code() == 409) {
                        Toast.makeText(context, "Username already exists!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Error", "Registration failed. ${t.message}", t)
            }
        })

    }
}