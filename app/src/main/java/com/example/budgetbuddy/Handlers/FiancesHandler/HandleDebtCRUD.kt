package com.example.budgetbuddy.Handlers.FiancesHandler

import android.content.Context
import android.util.Log
import com.example.budgetbuddy.API.DebtAPI.DebtAPI
import com.example.budgetbuddy.DataClasses.DebtData.DebtData
import com.example.budgetbuddy.DataClasses.UserData.UserDebtDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HandleDebtCRUD(requireContext: Context) {
    interface DebtDataCallBack {
        fun onDebtDataReceived(debtDataResponse: UserDebtDataResponse)
    }

    fun saveDebtData(username: String, debtType: String, amount: Double) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.228:65432/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(DebtAPI::class.java)
        val call: Call<DebtData> = apiService.saveDebtDataByUsername(username, debtType, amount)

        call.enqueue(object: Callback<DebtData> {
            override fun onResponse(call: Call<DebtData>, response: retrofit2.Response<DebtData>) {
                Log.d("HandleDebtCRUD", response.body().toString())
            }

            override fun onFailure(call: Call<DebtData>, t: Throwable) {
                Log.d("HandleDebtCRUD", "Failed to save debt data: ${t.message}")
            }
        })
    }

    fun fetchDebtData(username: String, callBack: DebtDataCallBack) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.228:65432/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(DebtAPI::class.java)
        val call: Call<UserDebtDataResponse> = apiService.getDebtDataByUsername(username)

        call.enqueue(object : Callback<UserDebtDataResponse> {
            override fun onResponse(call: Call<UserDebtDataResponse>, response: Response<UserDebtDataResponse>) {
                if (response.isSuccessful) {
                    val expenseDataResponse = response.body()
                    expenseDataResponse?.let {
                        callBack.onDebtDataReceived(expenseDataResponse)
                    }
                } else {
                    Log.d("HandleDebtCRUD", "Failed to fetch debt data")
                }
            }

            override fun onFailure(call: Call<UserDebtDataResponse>, t: Throwable) {
                Log.d("HandleDebtCRUD", "Failed to fetch debt data: ${t.message}")
            }
        })
    }

    fun deleteDebtData(username: String, incomeType: String, amount: Double) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.228:65432/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(DebtAPI::class.java)
        val call: Call<DebtData> = apiService.deleteDebtDataByUsername(username, incomeType, amount)

        call.enqueue(object: Callback<DebtData> {
            override fun onResponse(call: Call<DebtData>, response: retrofit2.Response<DebtData>) {
                if (response.isSuccessful) {
                    Log.d("HandleDebtCRUD", "Debt data deleted successfully")
                } else {
                    Log.d("HandleDebtCRUD", "Failed to delete debt data")
                }
            }

            override fun onFailure(call: Call<DebtData>, t: Throwable) {
                Log.d("HandleDebtCRUD", "Failed to delete debt data: ${t.message}")
            }
        })
    }
}