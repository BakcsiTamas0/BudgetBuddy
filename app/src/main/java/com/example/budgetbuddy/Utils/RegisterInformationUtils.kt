package com.example.budgetbuddy.Utils

class RegisterInformationUtils() {

    fun checkIfValidPassword(password: String, passwordConfirmation:String): Boolean {
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z]).{8,}\$")
        return regex.matches(password) && password == passwordConfirmation
    }

    fun checkIfValidEmailAddress(emailAddress: String): Boolean{
        val regex = Regex("^[A-Za-z0-9+_.-]+@(.+)$")
        return regex.matches(emailAddress)
    }
}