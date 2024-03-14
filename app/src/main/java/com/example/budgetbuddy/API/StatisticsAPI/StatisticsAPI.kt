package com.example.budgetbuddy.API.StatisticsAPI

import com.example.budgetbuddy.DataClasses.StatisticsData.StatisticsDataResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StatisticsAPI {
    @GET("statistics/generate_statistics/{username}/{download_name}")
    fun generateStatistics(
        @Path("username") username: String,
        @Path("download_name") downloadName: String
    ): Call<ResponseBody>

    @GET("statistics/get_expense_statistics/{username}")
    fun getExpenseStatistics(
        @Path("username") username: String
    ): Call<StatisticsDataResponse>
}