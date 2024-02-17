package com.example.budgetbuddy.Handlers.FiancesHandler

import android.content.Context
import android.util.Log
import com.example.budgetbuddy.API.ApiServices
import com.example.budgetbuddy.DataClasses.ExpenseData.ExpenseData
import com.example.budgetbuddy.DataClasses.UserData.UserExpenseDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HandleExpenseCRUD(requireContext: Context) {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://bakcsitamas.pythonanywhere.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiServices::class.java)

    interface ExpenseDataCallBack {
        fun onExpenseDataReceived(expenseDataResponse: UserExpenseDataResponse)
    }

    fun saveExpenseData(username: String, expenseType: String, amount: Double) {
        val call: Call<ExpenseData> = apiService.saveExpenseDataByUsername(username, expenseType, amount)

        call.enqueue(object: Callback<ExpenseData> {
            override fun onResponse(call: Call<ExpenseData>, response: retrofit2.Response<ExpenseData>) {
                Log.d("HandleIncomeCRUD", response.body().toString())
            }

            override fun onFailure(call: Call<ExpenseData>, t: Throwable) {
                Log.d("HandleIncomeCRUD", "Failed to save income data: ${t.message}")
            }
        })
    }

    fun fetchExpenseData(username: String, callBack: ExpenseDataCallBack) {
        val call: Call<UserExpenseDataResponse> = apiService.getExpenseDataByUsername(username)

        call.enqueue(object : Callback<UserExpenseDataResponse> {
            override fun onResponse(call: Call<UserExpenseDataResponse>, response: Response<UserExpenseDataResponse>) {
                if (response.isSuccessful) {
                    val expenseDataResponse = response.body()
                    expenseDataResponse?.let {
                        callBack.onExpenseDataReceived(expenseDataResponse)
                    }
                } else {
                    Log.d("HandleIncomeCRUD", "Failed to fetch income data")
                }
            }

            override fun onFailure(call: Call<UserExpenseDataResponse>, t: Throwable) {
                Log.d("HandleIncomeCRUD", "Failed to fetch income data: ${t.message}")
            }
        })
    }

    fun deleteExpenseData(username: String, incomeType: String, amount: Double) {
        val call: Call<ExpenseData> = apiService.deleteExpenseDataByUsername(username, incomeType, amount)

        call.enqueue(object: Callback<ExpenseData> {
            override fun onResponse(call: Call<ExpenseData>, response: retrofit2.Response<ExpenseData>) {
                if (response.isSuccessful) {
                    Log.d("HandleIncomeCRUD", "Income data deleted successfully")
                } else {
                    Log.d("HandleIncomeCRUD", "Failed to delete income data")
                }
            }

            override fun onFailure(call: Call<ExpenseData>, t: Throwable) {
                Log.d("HandleIncomeCRUD", "Failed to delete income data: ${t.message}")
            }
        })
    }
}