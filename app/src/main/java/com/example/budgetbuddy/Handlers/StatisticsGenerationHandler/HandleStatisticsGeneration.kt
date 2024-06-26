package com.example.budgetbuddy.Handlers.StatisticsGenerationHandler

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.budgetbuddy.API.StatisticsAPI.StatisticsAPI
import com.example.budgetbuddy.DataClasses.StatisticsData.PredictionResponse
import com.example.budgetbuddy.DataClasses.StatisticsData.StatisticsDataResponse
import com.example.budgetbuddy.Utils.RetrofitUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class HandleStatisticsGeneration (
    private val context: Context
) {
    private val retrofit = RetrofitUtils.initRetrofit()
    private val statisticsAPI = retrofit.create(StatisticsAPI::class.java)

    interface StatisticsDataCallBack {
        fun onStatisticsDataReceived(statisticsDataResponse: StatisticsDataResponse?)
    }

    interface EstimatedExpenseCallback {
        fun onEstimatedExpenseCallback(response: String)
    }

    fun downloadStatistics(username: String, downloadName: String) {
        val call = statisticsAPI.generateStatistics(username, downloadName)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.let {responseBody ->
                        saveFile(responseBody, context)
                        Log.d("HandleStatisticsGeneration", "File downloaded successfully")
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("HandleStatisticsGeneration", "Failed to download file: ${t.message}")
            }
        })
    }

    fun getExpenseStatistics(username: String, callback: StatisticsDataCallBack) {
        val call = statisticsAPI.getExpenseStatistics(username)

        call.enqueue(object: Callback<StatisticsDataResponse> {
            override fun onResponse(call: Call<StatisticsDataResponse>, response: Response<StatisticsDataResponse>) {
                if (response.isSuccessful) {
                    Log.d("HandleStatisticsGeneration", "${response.body()}")
                    val responseBody = response.body()
                    if (responseBody != null) {
                        callback.onStatisticsDataReceived(responseBody)
                        Log.d("HandleStatisticsGeneration", "Response: $responseBody")
                    } else {
                        Log.d("HandleStatisticsGeneration", "Response body is null")
                    }
                } else {
                    Log.d("HandleStatisticsGeneration", "Response not successful")
                }
            }

            override fun onFailure(call: Call<StatisticsDataResponse>, t: Throwable) {
                Log.d("HandleStatisticsGeneration", "Failed to get expense statistics: ${t.message}")
            }
        })
    }

    fun getWeeklyEstimatedSpending(username : String, callback: EstimatedExpenseCallback) {
        val call = statisticsAPI.getWeeklyEstimatedExpense(username)

        call.enqueue(object: Callback<PredictionResponse> {
            override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        val predictionValue = responseBody.response.toString()
                        callback.onEstimatedExpenseCallback(predictionValue)
                    }
                }
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                Log.d("HandleStatisticsGeneration", "Failed to get weekly estimated spending: ${t.message}")
            }
        })
    }

    private fun saveFile(responseBody: ResponseBody, context: Context) {
        try {
            val fileName = "statistics.pdf"
            val directory = File(context.filesDir, "BudgetBuddyApp/Statistics")
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val file = File(directory, fileName)

            responseBody.byteStream().use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }

            val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)

            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "No PDF viewer found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            Log.e("HandleStatisticsGeneration", "Error saving file: ${e.message}")
        }
    }
}
