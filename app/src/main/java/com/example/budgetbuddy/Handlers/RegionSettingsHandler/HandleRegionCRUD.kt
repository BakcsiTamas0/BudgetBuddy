package com.example.budgetbuddy.Handlers.RegionSettingsHandler

import android.util.Log
import com.example.budgetbuddy.API.RegionAPI.RegionAPI
import com.example.budgetbuddy.DataClasses.RegionData.RegionData
import com.example.budgetbuddy.DataClasses.RegionData.RegionResponse
import com.example.budgetbuddy.DataClasses.RegionData.RegionUsername
import com.example.budgetbuddy.Utils.RetrofitUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HandleRegionCRUD {
    private var retrofit = RetrofitUtils.initRetrofit()
    private val regionAPI: RegionAPI = retrofit.create(RegionAPI::class.java)

    interface onRegionResponseReceived {
        fun onRegionResponseReceived(regionResponse: RegionResponse)
    }

    fun setRegion(username: String, country: String, currency: String) {
        val call = regionAPI.saveUserRegionSettings(username, country, currency)

        call.enqueue(object : Callback<RegionData> {
            override fun onResponse(call: Call<RegionData>, response: Response<RegionData>) {
                Log.d("HandleRegionCrud", response.body().toString())
            }

            override fun onFailure(call: Call<RegionData>, t: Throwable) {
                Log.d("HandleRegionCrud", "Failed to save region: ${t.message}")
            }
        })
    }

    fun getRegion(username: String, callBack: onRegionResponseReceived) {
        val call = regionAPI.getUserRegionSettings(username)

        call.enqueue(object : Callback<RegionResponse> {
            override fun onResponse(call: Call<RegionResponse>, response: Response<RegionResponse>) {
                if (response.isSuccessful) {
                    val regionResponse = response.body()
                    regionResponse?.let {
                        callBack.onRegionResponseReceived(regionResponse)
                    }
                } else {
                    Log.d(
                        "HandleRegionCrud","Failed to get region. Response code: ${response.code()}"
                    )
                }
            }

            override fun onFailure(call: Call<RegionResponse>, t: Throwable) {
                Log.d("HandleRegionCrud", "Failed to get region: ${t.message}")
            }
        })
    }

    fun updateRegion(username: String, country: String, currency: String) {
        val call = regionAPI.updateUserRegionSettings(username, country, currency)

        call.enqueue(object : Callback<RegionData> {
            override fun onResponse(call: Call<RegionData>, response: Response<RegionData>) {
                Log.d("HandleRegionCrud", response.body().toString())
            }

            override fun onFailure(call: Call<RegionData>, t: Throwable) {
                Log.d("HandleRegionCrud", "Failed to update region: ${t.message}")
            }
        })
    }

    fun deleteRegion(username: String) {
        val call = regionAPI.deleteUserRegionSettings(username)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("HandleRegionCrud", response.body().toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("HandleRegionCrud", "Failed to delete region: ${t.message}")
            }
        })
    }
}