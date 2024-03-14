package com.example.budgetbuddy.DataClasses.StatisticsData

data class StatisticsDataResponse(
    val expense_statistics: ExpenseStatistics
)

data class ExpenseStatistics(
    val weekly_expense: List<WeeklyExpense>,
    val monthly_expense: MonthlyExpense
)

data class WeeklyExpense(
    val expense: Expense,
    val total_amount: Int
)

data class MonthlyExpense(
    val expense: Expense,
    val total_amount: Int
)

data class Expense(
    val expenses: List<List<Any>>
)
