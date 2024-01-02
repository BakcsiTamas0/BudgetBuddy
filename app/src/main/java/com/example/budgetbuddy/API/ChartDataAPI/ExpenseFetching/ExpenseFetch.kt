package com.example.budgetbuddy.API.ChartDataAPI.ExpenseFetching

import com.example.budgetbuddy.DataClasses.ChartData.ChartDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ExpenseFetch {
    @GET("/expense_chart_data/{username}")
    fun getExpenseChartData(@Path("username") username: String): Call<ChartDataResponse>
}



