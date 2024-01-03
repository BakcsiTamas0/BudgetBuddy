package com.example.budgetbuddy.DataClasses.ChartData


data class ExpenseChartDataResponse(
    val totalExpenseData: List<ExpenseData>
)

data class ExpenseData(
    val expenseType: String,
    val amount: Double
)
