package com.example.budgetbuddy.DataClasses.ChartData


data class ChartDataResponse(
    val totalExpenseData: List<ExpenseData>
)

data class ExpenseData(
    val expenseType: String,
    val amount: Double
)
