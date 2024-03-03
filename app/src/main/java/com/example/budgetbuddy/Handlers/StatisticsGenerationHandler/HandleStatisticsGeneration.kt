package com.example.budgetbuddy.Handlers.StatisticsGenerationHandler

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.example.budgetbuddy.API.StatisticsAPI.StatisticsAPI
import com.example.budgetbuddy.Utils.RetrofitUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class HandleStatisticsGeneration (
    private val context: Context
) {
    private val retrofit = RetrofitUtils.initRetrofit()
    private val statisticsAPI = retrofit.create(StatisticsAPI::class.java)

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

    private fun saveFile(responseBody: ResponseBody, context: Context) {
        try {
            if (isExternalStorageWritable()) {
                val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "statistics.pdf")
                val outputStream: OutputStream = FileOutputStream(file)

                responseBody.byteStream().use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                Log.d("HandleStatisticsGeneration", "File saved successfully")
            } else {
                Log.e("HandleStatisticsGeneration", "External storage not writable")
            }
        } catch (e: IOException) {
            Log.e("HandleStatisticsGeneration", "Error saving file: ${e.message}")
        }
    }
    private fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }
}
