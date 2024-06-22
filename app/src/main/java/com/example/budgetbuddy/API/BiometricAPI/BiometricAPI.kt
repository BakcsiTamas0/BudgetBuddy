package com.example.budgetbuddy.API.BiometricAPI

import com.example.budgetbuddy.DataClasses.SettingsData.UsernameResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BiometricAPI {
    @POST("/biometric/save_auth_key/{username}/{key}")
    fun saveAuthKeyToDatabase(
        @Path("username") username: String,
        @Path("key") key: String
    ): Call<Void>

    @POST("/biometric/auth_user/{key}")
    fun authBiometric(
        @Path("key") key: String
    ): Call<Void>

    @GET("get_username_by_key/{key}")
    fun getUsernameByKey(
        @Path("key") id: String
    ): Call<UsernameResponse>
}