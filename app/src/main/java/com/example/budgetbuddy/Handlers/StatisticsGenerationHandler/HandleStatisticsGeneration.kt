package com.example.budgetbuddy.Handlers.StatisticsGenerationHandler

import android.util.Log
import com.example.budgetbuddy.API.SettingsAPI.SettingsAPI
import com.example.budgetbuddy.API.StatisticsAPI.StatisticsAPI
import com.example.budgetbuddy.Utils.RetrofitUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HandleStatisticsGeneration {
    private val retrofit = RetrofitUtils.initRetrofit()
    private val statisticsAPI = retrofit.create(StatisticsAPI::class.java)

    fun generateStatistics(username: String) {
        val call = statisticsAPI.generateStatistics(username)

        call.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("HandleStatisticsGeneration", response.body().toString())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("HandleStatisticsGeneration", "Failed to generate statistics: ${t.message}")
            }
        })
    }

}