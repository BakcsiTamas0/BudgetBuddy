package com.example.budgetbuddy.API.SpendingLimitAPI

import com.example.budgetbuddy.DataClasses.SpendingLimitData.SpendingLimitResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WeeklySpendingLimitAPI {
    @POST("save_weekly_spending_limit/{username}/{amount}")
    fun saveSpendingLimit(
        @Path("username") username: String,
        @Path("amount") amount: Int
    ): Call<Void>

    @GET("get_weekly_spending_limit/{username}")
    fun getSpendingLimit(
        @Path("username") username: String
    ): Call<SpendingLimitResponse>
}