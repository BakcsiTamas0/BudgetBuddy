package com.example.budgetbuddy.API.SettingsAPI

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface SettingsAPI {
    @POST("/settings/update_username/{old_username}/{new_username}")
    fun updateUsername(
        @Path("old_username") oldUsername: String,
        @Path("new_username") newUsername: String
    ): Call<Void>

    @POST("/settings/update_password/{username}/{new_password}")
    fun updatePassword(
        @Path("username") username: String,
        @Path("new_password") newPassword: String
    ): Call<Void>

    @POST("/settings/update_email/{username}/{new_email}")
    fun updateEmail(
        @Path("username") username: String,
        @Path("new_email") newEmail: String
    ): Call<Void>
}
