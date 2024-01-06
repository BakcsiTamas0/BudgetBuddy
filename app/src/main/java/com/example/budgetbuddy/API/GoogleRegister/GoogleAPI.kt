package com.example.budgetbuddy.API.GoogleRegister

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GoogleAPI {
    @POST("register_google_user/{username}/{email}")
    fun registerGoogleUser(
        @Path("username") username: String,
        @Path("email") email: String
    ): Call<Void>
}