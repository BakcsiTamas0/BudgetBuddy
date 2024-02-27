package com.example.budgetbuddy.Handlers.Settings

import android.content.Context
import android.util.Log
import com.example.budgetbuddy.API.SettingsAPI.SettingsAPI
import com.example.budgetbuddy.Utils.RetrofitUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HandleSettings {
    private val retrofit = RetrofitUtils.initRetrofit()
    private val settingsAPI = retrofit.create(SettingsAPI::class.java)

    fun updateUsername(oldUsername: String, newUsername: String) {
        val call = settingsAPI.updateUsername(oldUsername, newUsername)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("HandleSettings", response.body().toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("HandleSettings", "Failed to update username: ${t.message}")
            }
        })
    }

    fun updatePassword(username: String, newPassword: String) {
        val call = settingsAPI.updatePassword(username, newPassword)

        call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("HandleSettings", response.body().toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("HandleSettings", "Failed to update password: ${t.message}")
            }
        })
    }

    fun updateEmail(username: String, newEmail: String) {
        val call = settingsAPI.updateEmail(username, newEmail)

        call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("HandleSettings", response.body().toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("HandleSettings", "Failed to update email: ${t.message}")
            }
        })
    }
}