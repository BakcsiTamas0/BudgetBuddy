package com.example.budgetbuddy.Handlers.ProfileHandler

import android.content.Context
import android.util.Log
import com.example.budgetbuddy.API.ProfileAPI.ProfileAPI
import com.example.budgetbuddy.DataClasses.ProfileData.ProfileData
import com.example.budgetbuddy.DataClasses.ProfileData.ProfileDataResponse
import com.example.budgetbuddy.Handlers.FiancesHandler.HandleDebtCRUD
import com.example.budgetbuddy.Utils.RetrofitUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HandleProfileCRUD(requireContext: Context) {
    val retrofit: Retrofit = RetrofitUtils.initRetrofit()
    private val profileAPI: ProfileAPI = retrofit.create(ProfileAPI::class.java)

    interface OnSubUserDataReceiver {
        fun onSubUserDataReceived(profileDataResponse: ProfileDataResponse)
    }

    fun saveSubUser(username: String, subUsername: String){
        val call: Call<ProfileData> = profileAPI.addSubUser(username, subUsername)

        call.enqueue(object : Callback<ProfileData> {
            override fun onResponse(call: Call<ProfileData>, response: Response<ProfileData>) {
                Log.d("HandleProfileCrud", response.body().toString())
            }

            override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                Log.d("HandleProfileCrud", "Failed to save sub user: ${t.message}")
            }
        })
    }

    fun getSubUsers(username: String, callBack: OnSubUserDataReceiver){
        val call = profileAPI.getSubUsers(username)

        call.enqueue(object: Callback<ProfileDataResponse>{
            override fun onResponse(call: Call<ProfileDataResponse>, response: Response<ProfileDataResponse>) {
                if (response.isSuccessful) {
                    val profileDataResponse = response.body()
                    profileDataResponse?.let {
                        callBack.onSubUserDataReceived(profileDataResponse)
                    }
                } else {
                    Log.d("HandleProfileCrud", "Failed to get sub users")
                }
            }

            override fun onFailure(call: Call<ProfileDataResponse>, t: Throwable) {
                Log.d("HandleProfileCrud", "Failed to get sub users: ${t.message}")
            }
        })
    }

    fun deleteSubUsers(username: String, subUsername: String) {
        val call = profileAPI.deleteSubUser(username, subUsername)

        call.enqueue(object : Callback<ProfileData> {
            override fun onResponse(call: Call<ProfileData>, response: Response<ProfileData>) {
                Log.d("HandleProfileCrud", response.body().toString())
            }

            override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                Log.d("HandleProfileCrud", "Failed to delete sub user: ${t.message}")
            }
        })
    }
}