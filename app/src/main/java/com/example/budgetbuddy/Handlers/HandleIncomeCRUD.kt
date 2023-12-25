package com.example.budgetbuddy.Handlers

import android.content.Context
import android.util.Log
import com.example.budgetbuddy.API.ApiServices
import com.example.budgetbuddy.DataClasses.IncomeData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HandleIncomeCRUD(requireContext: Context) {

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

}