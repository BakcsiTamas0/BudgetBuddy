package com.example.budgetbuddy.Handlers.ChartHandler

import android.content.Context
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.budgetbuddy.API.ChartDataAPI.ExpenseFetching.ExpenseFetch
import com.example.budgetbuddy.DataClasses.ChartData.ExpenseChartDataResponse
import com.example.budgetbuddy.DataClasses.ChartData.ExpenseData

class ExpenseChartFetcher(private val context: Context, private val username: String) {

    fun fetchExpenseChartData(onChartDataFetched: (List<ExpenseData>) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.228:65432/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val expenseFetchService = retrofit.create(ExpenseFetch::class.java)

        val call = expenseFetchService.getExpenseChartData(username)
        call.enqueue(object : Callback<ExpenseChartDataResponse> {
            override fun onResponse(
                call: Call<ExpenseChartDataResponse>,
                response: Response<ExpenseChartDataResponse>
            ) {
                if (response.isSuccessful) {
                    val chartDataResponse = response.body()
                    chartDataResponse?.let {
                        onChartDataFetched(it.totalExpenseData)
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Failed to fetch expense data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ExpenseChartDataResponse>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Failed to fetch expense data: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
