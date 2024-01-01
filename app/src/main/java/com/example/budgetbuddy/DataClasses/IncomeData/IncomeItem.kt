package com.example.budgetbuddy.DataClasses.IncomeData

data class IncomeItem(
    val text: String,
    val imageId: Int,
    var amount: Double? = null
)
