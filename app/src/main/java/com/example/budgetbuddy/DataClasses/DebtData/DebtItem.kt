package com.example.budgetbuddy.DataClasses.DebtData

data class DebtItem(
    val text: String,
    val imageId: Int,
    var amount: Double? = null
)
