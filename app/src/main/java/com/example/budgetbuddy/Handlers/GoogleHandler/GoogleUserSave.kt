package com.example.budgetbuddy.Handlers.GoogleHandler

import android.util.Log
import com.example.budgetbuddy.API.GoogleRegister.GoogleAPI
import com.example.budgetbuddy.Utils.RetrofitUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GoogleUserSave {
    private val retrofit: Retrofit = RetrofitUtils.initRetrofit()
    private val userRegisterAPI = retrofit.create(GoogleAPI::class.java)

    fun saveGoogleUser(username: String, email: String, messageToken: String) {
        val call = userRegisterAPI.registerGoogleUser(username, email, messageToken)

        call.enqueue(object: Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("GoogleUserSave", "Google user saved successfully")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("GoogleUserSave", "Failed to save google user: ${t.message}")
            }
        })
    }
}