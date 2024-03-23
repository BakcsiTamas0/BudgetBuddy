package com.example.budgetbuddy.DataClasses.StatisticsData

data class StatisticsDataResponse(
    val expense_statistics: ExpenseStatistics
)

data class ExpenseStatistics(
    val week_number: List<Int>,
    val weekly_expense: WeeklyExpense,
    val month_number: List<Int>,
    val monthly_expense: MonthlyExpense
)

data class WeeklyExpense(
    val expenses_week: List<ExpenseItem>,
    val total_amount: Int
)

data class MonthlyExpense(
    val expenses_month: List<ExpenseItem>,
    val total_amount: Int
)

data class ExpenseItem(
    val expense: ExpenseData,
    val total_amount: Int
)

data class ExpenseData(
    val expenses: List<List<Any>>
)