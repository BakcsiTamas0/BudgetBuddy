package com.example.budgetbuddy.API.SpendingLimitAPI

import com.example.budgetbuddy.DataClasses.SpendingLimitData.SpendingLimitResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MonthlySpendingLimitAPI {
    @POST("save_monthly_spending_limit/{username}/{amount}")
    fun saveSpendingLimit(
        @Path("username") username: String,
        @Path("amount") amount: Int
    ): Call<Void>

    @GET("get_monthly_spending_limit/{username}")
    fun getSpendingLimit(
        @Path("username") username: String
    ): Call<SpendingLimitResponse>
}