package com.example.budgetbuddy.Handlers.SpendingLimitHandler

import android.util.Log
import com.example.budgetbuddy.API.SpendingLimitAPI.MonthlySpendingLimitAPI
import com.example.budgetbuddy.API.SpendingLimitAPI.WeeklySpendingLimitAPI
import com.example.budgetbuddy.DataClasses.SpendingLimitData.SpendingLimitResponse
import com.example.budgetbuddy.Utils.RetrofitUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HandleSpendingLimit {
    private val retrofit = RetrofitUtils.initRetrofit()
    private val weeklySpendingLimitAPI = retrofit.create(WeeklySpendingLimitAPI::class.java)
    private val monthlySpendingLimitAPI = retrofit.create(MonthlySpendingLimitAPI::class.java)

    interface onSpendingLimitDataReceived {
        fun onSpendingLimitDataReceived(spendingLimitDataResponse: SpendingLimitResponse)
    }

    fun saveWeeklySpendingLimit(username: String, spendingLimit: Int) {
        val call = weeklySpendingLimitAPI.saveSpendingLimit(username, spendingLimit)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("HandleSpendingLimit", "Weekly spending limit saved successfully")
                } else {
                    Log.d("HandleSpendingLimit", "Failed to save weekly spending limit")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("HandleSpendingLimit", "Failed to save weekly spending limit: ${t.message}")
            }
        })
    }

    fun saveMonthlySpendingLimit(username: String, spendingLimit: Int) {
        val call = monthlySpendingLimitAPI.saveSpendingLimit(username, spendingLimit)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("HandleSpendingLimit", "Monthly spending limit saved successfully")
                } else {
                    Log.d("HandleSpendingLimit", "Failed to save monthly spending limit")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("HandleSpendingLimit", "Failed to save monthly spending limit: ${t.message}")
            }
        })
    }

    fun fetchWeeklySpendingLimit(username: String, callBack: onSpendingLimitDataReceived) {
        val call = weeklySpendingLimitAPI.getSpendingLimit(username)

        call.enqueue(object : Callback<SpendingLimitResponse> {
            override fun onResponse(call: Call<SpendingLimitResponse>, response: Response<SpendingLimitResponse>) {
                if (response.isSuccessful) {
                    val spendingLimitResponse = response.body()
                    spendingLimitResponse?.let {
                        callBack.onSpendingLimitDataReceived(spendingLimitResponse)
                    }
                } else {
                    Log.d("HandleSpendingLimit", "Failed to fetch weekly spending limit")
                }
            }

            override fun onFailure(call: Call<SpendingLimitResponse>, t: Throwable) {
                Log.d("HandleSpendingLimit", "Failed to fetch weekly spending limit: ${t.message}")
            }
        })
    }

    fun fetchMonthlySpendingLimit(username: String, callBack: onSpendingLimitDataReceived) {
        val call = monthlySpendingLimitAPI.getSpendingLimit(username)

        call.enqueue(object : Callback<SpendingLimitResponse> {
            override fun onResponse(call: Call<SpendingLimitResponse>, response: Response<SpendingLimitResponse>) {
                if (response.isSuccessful) {
                    val spendingLimitResponse = response.body()
                    spendingLimitResponse?.let {
                        callBack.onSpendingLimitDataReceived(spendingLimitResponse)
                    }
                } else {
                    Log.d("HandleSpendingLimit", "Failed to fetch monthly spending limit")
                }
            }

            override fun onFailure(call: Call<SpendingLimitResponse>, t: Throwable) {
                Log.d("HandleSpendingLimit", "Failed to fetch monthly spending limit: ${t.message}")
            }
        })
    }
}