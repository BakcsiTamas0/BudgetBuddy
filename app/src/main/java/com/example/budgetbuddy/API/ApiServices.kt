package com.example.budgetbuddy.API

import com.example.budgetbuddy.DataClasses.ExpenseData.ExpenseData
import com.example.budgetbuddy.DataClasses.UserData.EmailResponse
import com.example.budgetbuddy.DataClasses.IncomeData.IncomeData
import com.example.budgetbuddy.DataClasses.UserData.UserExpenseDataResponse
import com.example.budgetbuddy.DataClasses.UserData.UserIncomeDataResponse
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServices {
    // Authentication API endpoints
    @POST("register_user/{username}/{password}/{email}/{message_token}")
    fun registerUser(
        @Path("username") username: String,
        @Path("password") password: String,
        @Path("email") email: String,
        @Path("message_token") messageToken: String
    ): Call<Void>

    @POST("authenticate_user/{username}/{password}")
    fun authenticateUser(
        @Path("username") username: String,
        @Path("password") password: String
    ): Call<Void>

    // User API endpoint
    @POST("get_email_by_username/{username}")
    fun getEmailByUsername(
        @Path("username") username: String
    ): Call<EmailResponse>

    // Income API endpoint
    @POST("save_income_data_by_username/{username}/{incomeType}/{amount}")
    fun saveIncomeDataByUsername(
        @Path("username") username: String,
        @Path("incomeType") incomeType: String,
        @Path("amount") amount: Double
    ): Call<IncomeData>

    @POST("get_income_data_by_username/{username}")
    fun getIncomeDataByUsername(
        @Path ("username") username: String
    ): Call<UserIncomeDataResponse>

    @POST("delete_income_data_by_username/{username}/{incomeType}/{amount}")
    fun deleteIncomeDataByUsername(
        @Path("username") username: String,
        @Path("incomeType") incomeType: String,
        @Path("amount") amount: Double):
            Call<IncomeData>

    // Expense API endpoint
    @POST("save_expense_data_by_username/{username}/{expenseType}/{amount}")
    fun saveExpenseDataByUsername(
        @Path("username") username: String,
        @Path("expenseType") expenseType: String,
        @Path("amount") amount: Double,
    ): Call<ExpenseData>

    @POST("get_expense_data_by_username/{username}")
    fun getExpenseDataByUsername(
        @Path("username") username: String
    ): Call<UserExpenseDataResponse>

    @POST("delete_expense_data_by_username/{username}/{incomeType}/{amount}")
    fun deleteExpenseDataByUsername(
        @Path("username") username: String,
        @Path("incomeType") incomeType: String,
        @Path("amount") amount: Double):
            Call<ExpenseData>
}