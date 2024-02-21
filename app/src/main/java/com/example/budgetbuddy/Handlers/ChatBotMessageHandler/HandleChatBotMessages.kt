package com.example.budgetbuddy.Handlers.ChatBotMessageHandler

import android.app.Activity
import android.util.Log
import com.example.budgetbuddy.API.ChatbotAPI.ChatbotAPI
import com.example.budgetbuddy.DataClasses.ChatbotData.ChatRequest
import com.example.budgetbuddy.DataClasses.ChatbotData.ChatResponse
import com.example.budgetbuddy.Utils.RetrofitUtils
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HandleChatBotMessages(private val activity: Activity) {
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    fun sendChatBotMessage(username: String, message: String, callback: (String) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.228:65432/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val apiService = retrofit.create(ChatbotAPI::class.java)
        val call: Call<ChatResponse> = apiService.sendChatBotMessage(username, message)

        call.enqueue(object : Callback<ChatResponse> {
            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val serverResponse = responseBody!!.response
                    Log.d("HandleChatBotMessages", "Chatbot response: $serverResponse")
                    callback.invoke(serverResponse)
                } else {
                    Log.d("HandleChatBotMessages", "Unsuccessful response: ${response.code()}")
                    Log.d("HandleChatBotMessages", "Error body: ${response.errorBody()?.string()}")
                    Log.d("HandleChatBotMessages", "Message: $message")
                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                Log.d("HandleChatBotMessages", "Failed to send chat bot message: ${t.message}")
            }
        })
    }
}
