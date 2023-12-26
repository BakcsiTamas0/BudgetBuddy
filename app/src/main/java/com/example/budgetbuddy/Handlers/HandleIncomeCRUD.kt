package com.example.budgetbuddy.Handlers

import android.content.Context
import android.util.Log
import com.example.budgetbuddy.API.ApiServices
import com.example.budgetbuddy.DataClasses.IncomeData
import com.example.budgetbuddy.DataClasses.UserIncomeDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HandleIncomeCRUD(requireContext: Context) {

    interface IncomeDataCallBack {
        fun onIncomeDataReceived(incomeDataResponse: UserIncomeDataResponse)
    }

    fun saveIncomeData(username: String, incomeType: String, amount: Double) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.228:65432/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiServices::class.java)
        val incomeData = IncomeData(username, incomeType, amount.toString())
        val call: Call<IncomeData> = apiService.saveIncomeDataByUsername(incomeData)

        call.enqueue(object: Callback<IncomeData> {
            override fun onResponse(call: Call<IncomeData>, response: retrofit2.Response<IncomeData>) {
                Log.d("HandleIncomeCRUD", response.body().toString())
                if (response.isSuccessful) {
                    Log.d("HandleIncomeCRUD", "Income data saved successfully")
                } else {
                    Log.d("HandleIncomeCRUD", "Failed to save income data")
                }
            }

            override fun onFailure(call: Call<IncomeData>, t: Throwable) {
                Log.d("HandleIncomeCRUD", "Failed to save income data: ${t.message}")
                t.printStackTrace()
            }
        })
    }

    fun fetchIncomeData(username: String, callBack: IncomeDataCallBack) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.228:65432/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiServices::class.java)
        val call: Call<UserIncomeDataResponse> = apiService.getIncomeDataByUsername(username)

        call.enqueue(object : Callback<UserIncomeDataResponse> {
            override fun onResponse(call: Call<UserIncomeDataResponse>, response: Response<UserIncomeDataResponse>) {
                if (response.isSuccessful) {
                    val incomeDataResponse = response.body()
                    incomeDataResponse?.let {
                        callBack.onIncomeDataReceived(incomeDataResponse)
                    }
                } else {
                    Log.d("HandleIncomeCRUD", "Failed to fetch income data")
                }
            }

            override fun onFailure(call: Call<UserIncomeDataResponse>, t: Throwable) {
                Log.d("HandleIncomeCRUD", "Failed to fetch income data: ${t.message}")
            }
        })
    }
}