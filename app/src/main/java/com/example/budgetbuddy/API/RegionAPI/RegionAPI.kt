package com.example.budgetbuddy.API.RegionAPI

import com.example.budgetbuddy.DataClasses.RegionData.RegionData
import com.example.budgetbuddy.DataClasses.RegionData.RegionResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface RegionAPI {
    @POST("save_user_region_settings/{username}/{country}/{currency}")
    fun saveUserRegionSettings(
        @Path("username") username: String,
        @Path("country") country: String,
        @Path("currency") currency: String
    ): Call<RegionData>

    @POST("get_user_region_settings/{username}")
    fun getUserRegionSettings(
        @Path("username") username: String
    ): Call<RegionResponse>

    @POST("update_user_region_settings/{username}/{country}/{currency}")
    fun updateUserRegionSettings(
        @Path("username") username: String,
        @Path("country") country: String,
        @Path("currency") currency: String
    ): Call<RegionData>

    @POST("delete_user_region_settings/{username}")
    fun deleteUserRegionSettings(
        @Path("username") username: String
    ): Call<Void>
}