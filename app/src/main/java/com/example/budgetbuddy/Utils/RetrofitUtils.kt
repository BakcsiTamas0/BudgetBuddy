package com.example.budgetbuddy.Utils

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitUtils {
    companion object {
        private const val BASE_URL = "http://192.168.43.228:65432/"
        fun initRetrofit(): Retrofit {

            val gson = GsonBuilder()
                .setLenient()
                .create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}