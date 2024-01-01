package com.example.budgetbuddy.DataClasses.ExpenseData

data class ExpenseItem (
    val text: String,
    val imageId: Int,
    var amount: Double? = null
)