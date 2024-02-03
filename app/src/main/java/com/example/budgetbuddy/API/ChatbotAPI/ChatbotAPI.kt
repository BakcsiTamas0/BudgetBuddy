package com.example.budgetbuddy.API.ChatbotAPI

import com.example.budgetbuddy.DataClasses.ChatbotData.ChatRequest
import com.example.budgetbuddy.DataClasses.ChatbotData.ChatResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatbotAPI {
    @POST("chatbot/{username}/{user_message}")
    fun sendChatBotMessage(
        @Path("username") username: String,
        @Path("user_message") user_message: String
    ): Call<ChatResponse>
}