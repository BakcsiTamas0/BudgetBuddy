package com.example.budgetbuddy.API.ExchangeAPI

import android.util.Log
import com.example.budgetbuddy.DataClasses.ExchangeData.ExchangeItem
import com.example.budgetbuddy.DataClasses.ExchangeData.ExchangeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import kotlin.collections.emptyMap as emptyMap

interface ExchangeRateService {
    @GET("v6/{apiKey}/latest/{baseCurrency}")
    suspend fun getExchangeRates(
        @Path("apiKey") apiKey: String,
        @Path("baseCurrency") baseCurrency: String
    ): ExchangeResponse
}

class ExchangeAPI(private val apiKey: String) {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://v6.exchangerate-api.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: ExchangeRateService = retrofit.create(ExchangeRateService::class.java)
    suspend fun getExchangeRates(baseCurrency: String): List<ExchangeItem> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getExchangeRates(apiKey, baseCurrency)
                val exchangeRates = response.conversion_rates.map { ExchangeItem(it.key, it.value.toDouble()) }
                exchangeRates.toList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}