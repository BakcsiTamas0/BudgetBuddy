package com.example.budgetbuddy.API.GoogleRegister

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GoogleAPI {
    @POST("register_google_user/{username}/{email}/{account_token_id}/{message_token}")
    fun registerGoogleUser(
        @Path("username") username: String,
        @Path("email") email: String,
        @Path("account_token_id") accountTokenID: String,
        @Path("message_token") messageToken: String
    ): Call<Void>
}