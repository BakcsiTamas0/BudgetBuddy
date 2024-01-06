package com.example.budgetbuddy.DataClasses.UserData
data class UserRegisterDataClass(
    val username : String,
    val password : String,
    val email : String,
)

data class UserGoogleRegisterDataClass(
    val username : String,
    val email : String,
)