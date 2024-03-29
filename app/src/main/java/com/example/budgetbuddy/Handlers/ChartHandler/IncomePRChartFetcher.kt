package com.example.budgetbuddy.Handlers.ChartHandler

import android.content.Context
import android.widget.Toast
import com.example.budgetbuddy.API.ChartDataAPI.IncomeFetching.IncomeFetch
import com.example.budgetbuddy.DataClasses.ChartData.IncomeChartDataResponse
import com.example.budgetbuddy.DataClasses.ChartData.IncomeData
import com.example.budgetbuddy.Utils.RetrofitUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IncomePRChartFetcher(private val context: Context, private val username: String) {
    val retrofit: Retrofit = RetrofitUtils.initRetrofit()

    fun fetchIncomeChartData(onChartDataFetched: (List<IncomeData>)-> Unit) {
        val incomeFetchService = retrofit.create(IncomeFetch::class.java)

        val call = incomeFetchService.getIncomeChartData(username)
        call.enqueue(object: Callback<IncomeChartDataResponse>{
            override fun onResponse(
                call: Call<IncomeChartDataResponse>,
                response: Response<IncomeChartDataResponse>
            ) {
                if (response.isSuccessful) {
                    val chartDataResponse = response.body()
                    chartDataResponse.let {
                        onChartDataFetched(it!!.totalIncomeData)
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Failed to fetch income data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<IncomeChartDataResponse>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Failed to fetch income data: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
