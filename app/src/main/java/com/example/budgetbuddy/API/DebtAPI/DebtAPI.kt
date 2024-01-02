package com.example.budgetbuddy.API.DebtAPI

import com.example.budgetbuddy.DataClasses.DebtData.DebtData
import com.example.budgetbuddy.DataClasses.ExpenseData.ExpenseData
import com.example.budgetbuddy.DataClasses.UserData.UserDebtDataResponse
import com.example.budgetbuddy.DataClasses.UserData.UserExpenseDataResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface DebtAPI {
    @POST("save_debt_data_by_username/{username}/{expenseType}/{amount}")
    fun saveDebtDataByUsername(
        @Path("username") username: String,
        @Path("expenseType") expenseType: String,
        @Path("amount") amount: Double,
    ): Call<DebtData>

    @POST("get_debt_data_by_username/{username}")
    fun getDebtDataByUsername(
        @Path("username") username: String
    ): Call<UserDebtDataResponse>

    @POST("delete_debt_data_by_username/{username}/{incomeType}/{amount}")
    fun deleteDebtDataByUsername(
        @Path("username") username: String,
        @Path("incomeType") incomeType: String,
        @Path("amount") amount: Double):
            Call<DebtData>
}