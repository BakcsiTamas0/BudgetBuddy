package com.example.budgetbuddy.API.ProfileAPI

import com.example.budgetbuddy.DataClasses.ProfileData.ProfileData
import com.example.budgetbuddy.DataClasses.ProfileData.ProfileDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileAPI {
    @POST("add_sub_user/{username}/{subUsername}")
    fun addSubUser(
        @Path("username") username: String,
        @Path("subUsername") subUsername: String
    ) : Call<ProfileData>

    @GET("get_sub_users/{username}")
    fun getSubUsers(
        @Path("username") username: String
    ) : Call<ProfileDataResponse>

    @POST("delete_sub_user/{username}/{subUsername}")
    fun deleteSubUser(
        @Path("username") username: String,
        @Path("subUsername") subUsername: String
    ) : Call<ProfileData>
}