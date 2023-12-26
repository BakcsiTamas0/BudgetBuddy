package com.example.budgetbuddy.API

import com.example.budgetbuddy.DataClasses.EmailResponse
import com.example.budgetbuddy.DataClasses.IncomeData
import com.example.budgetbuddy.DataClasses.UserIncomeDataResponse
import com.example.budgetbuddy.DataClasses.UserLoginDataClass
import com.example.budgetbuddy.DataClasses.UserRegisterDataClass
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

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
    fun saveIncomeDataByUsername(@Body incomeData: IncomeData): Call<IncomeData>

    @POST("get_income_data_by_username")
    fun getIncomeDataByUsername(@Body username: String): Call<UserIncomeDataResponse>

    @POST("delete_income_data_by_username/{username}/{incomeType}/{amount}")
    fun deleteIncomeDataByUsername(
        @Path("username") username: String,
        @Path("incomeType") incomeType: String,
        @Path("amount") amount: Double):
            Call<IncomeData>
}