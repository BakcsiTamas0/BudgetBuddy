package com.example.budgetbuddy.DataClasses.UserData

data class EmailResponse(val email: String)

data class UserIncomeDataResponse(val income: List<List<Any>>)

data class UserExpenseDataResponse(val expense: List<List<Any>>)

data class UserDebtDataResponse(val debt: List<List<Any>>)