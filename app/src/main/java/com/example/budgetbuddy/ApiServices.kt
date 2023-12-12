package com.example.budgetbuddy

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
interface ApiServices {
    @POST("register_user")
    fun registerUser(@Body userRegisterDataClass: UserRegisterDataClass): Call<Void>
}