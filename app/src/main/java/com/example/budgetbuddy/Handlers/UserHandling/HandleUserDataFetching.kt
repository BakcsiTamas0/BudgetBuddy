package com.example.budgetbuddy.Handlers.UserHandling

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.budgetbuddy.API.ApiServices
import com.example.budgetbuddy.DataClasses.UserData.EmailResponse
import com.example.budgetbuddy.R
import com.example.budgetbuddy.Utils.RetrofitUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HandleUserDataFetching(
    private val context: Context,
    private val usernameTextView: TextView,
    private val emailTextView: TextView) {

    fun fetchData(username: String) {
        val intent = (context as AppCompatActivity).intent
        val intentUsername = intent.getStringExtra("USERNAME")

        val drawerUsername: TextView = (context).findViewById(R.id.drawerUsername)
        drawerUsername.text = intentUsername

        val retrofit: Retrofit = RetrofitUtils.initRetrofit()
        val apiServices = retrofit.create(ApiServices::class.java)

        val call = apiServices.getEmailByUsername(username)

        call.enqueue(object : Callback<EmailResponse> {
            override fun onResponse(call: Call<EmailResponse>, response: Response<EmailResponse>) {
                if (response.isSuccessful) {
                    val email: String? = response.body()?.email
                    if (!email.isNullOrBlank()) {
                        updateDrawerViews(username, email)
                    } else {
                        Log.d("TAG", "onResponse: Email not found")
                    }
                } else {
                    Log.d("TAG", "onResponse: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<EmailResponse>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })
    }

    fun updateDrawerViews(username: String, email: String) {
        usernameTextView.text = username
        emailTextView.text = email
    }
}
