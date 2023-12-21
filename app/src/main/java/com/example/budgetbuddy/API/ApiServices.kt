package com.example.budgetbuddy.API

import com.example.budgetbuddy.DataClasses.EmailResponse
import com.example.budgetbuddy.DataClasses.UserLoginDataClass
import com.example.budgetbuddy.DataClasses.UserRegisterDataClass
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
interface ApiServices {
    @POST("register_user")
    fun registerUser(@Body userRegisterDataClass: UserRegisterDataClass): Call<Void>

    @POST("authenticate_user")
    fun authenticateUser(@Body userLoginDataClass: UserLoginDataClass): Call<Void>

    @POST("get_email_by_username")
    fun getEmailByUsername(@Body usernameData: String): Call<EmailResponse>
}