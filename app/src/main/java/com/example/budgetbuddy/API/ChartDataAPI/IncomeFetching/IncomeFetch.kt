package com.example.budgetbuddy.API.ChartDataAPI.IncomeFetching

import com.example.budgetbuddy.DataClasses.ChartData.IncomeChartDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IncomeFetch {
    @GET("/income_chart_data/{username}")
    fun getIncomeChartData(@Path("username") username: String): Call<IncomeChartDataResponse>
}
