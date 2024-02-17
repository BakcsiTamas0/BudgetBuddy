package com.example.budgetbuddy.Handlers.GoogleHandler

import android.util.Log
import com.example.budgetbuddy.API.GoogleRegister.GoogleAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GoogleUserSave {
    fun saveGoogleUser(username: String, email: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bakcsitamas.pythonanywhere.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userRegisterAPI = retrofit.create(GoogleAPI::class.java)
        val call = userRegisterAPI.registerGoogleUser(username, email)

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