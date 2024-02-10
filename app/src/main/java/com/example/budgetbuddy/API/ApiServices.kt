package com.example.budgetbuddy.API

import com.example.budgetbuddy.DataClasses.ExpenseData.ExpenseData
import com.example.budgetbuddy.DataClasses.UserData.EmailResponse
import com.example.budgetbuddy.DataClasses.IncomeData.IncomeData
import com.example.budgetbuddy.DataClasses.UserData.UserExpenseDataResponse
import com.example.budgetbuddy.DataClasses.UserData.UserIncomeDataResponse
import com.example.budgetbuddy.DataClasses.UserData.UserLoginDataClass
import com.example.budgetbuddy.DataClasses.UserData.UserRegisterDataClass
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    // Authentication API endpoints
    @POST("register_user")
    fun registerUser(@Body userRegisterDataClass: UserRegisterDataClass): Call<Void>

    @POST("authenticate_user")
    fun authenticateUser(@Body userLoginDataClass: UserLoginDataClass): Call<Void>

    // User API endpoint
    @POST("get_email_by_username")
    fun getEmailByUsername(@Body username: String): Call<EmailResponse>

    // Income API endpoint
    @POST("save_income_data_by_username")
    fun saveIncomeDataByUsername(
        @Path("username") username: String,
        @Path("incomeType") incomeType: String,
        @Path("amount") amount: Double
    ): Call<IncomeData>

    @POST("get_income_data_by_username")
    fun getIncomeDataByUsername(@Body username: String): Call<UserIncomeDataResponse>

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