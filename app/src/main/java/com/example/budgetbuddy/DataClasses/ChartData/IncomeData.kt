package com.example.budgetbuddy.DataClasses.ChartData

data class IncomeChartDataResponse(
    val totalIncomeData: List<IncomeData>
)

data class IncomeData(
    val incomeType: String,
    val amount: Double
)