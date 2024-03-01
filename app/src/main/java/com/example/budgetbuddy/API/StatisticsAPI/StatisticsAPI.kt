package com.example.budgetbuddy.API.StatisticsAPI

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface StatisticsAPI {
    @POST("generate_statistics/{username}")
    fun generateStatistics(
        @Path("username") username: String
    ): Call<String>
}