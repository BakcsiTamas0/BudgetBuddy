package com.example.budgetbuddy.DataClasses.ExchangeData

data class ExchangeItem(val currency: String, val rate: Double)

data class ExchangeResponse(val conversion_rates: Map<String, Double>)