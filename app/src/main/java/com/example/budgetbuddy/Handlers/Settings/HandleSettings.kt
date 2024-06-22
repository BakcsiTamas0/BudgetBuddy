package com.example.budgetbuddy.Handlers.Settings

import android.util.Log
import com.example.budgetbuddy.API.BiometricAPI.BiometricAPI
import com.example.budgetbuddy.API.SettingsAPI.SettingsAPI
import com.example.budgetbuddy.BiometricAuthentication.HandleBiometricAuthentication
import com.example.budgetbuddy.DataClasses.SettingsData.UsernameResponse
import com.example.budgetbuddy.Utils.RetrofitUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder

class HandleSettings {
    private val retrofit = RetrofitUtils.initRetrofit()
    private val biometricAPI = retrofit.create(BiometricAPI::class.java)
    private val settingsAPI  = retrofit.create(SettingsAPI::class.java)

    // Account
    fun updateUsername(oldUsername: String, newUsername: String) {
        val call = settingsAPI.updateUsername(oldUsername, newUsername)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("HandleSettings", response.body().toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("HandleSettings", "Failed to update username: ${t.message}")
            }
        })
    }

    fun updatePassword(username: String, newPassword: String) {
        val call = settingsAPI.updatePassword(username, newPassword)

        call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("HandleSettings", response.body().toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("HandleSettings", "Failed to update password: ${t.message}")
            }
        })
    }

    fun updateEmail(username: String, newEmail: String) {
        val call = settingsAPI.updateEmail(username, newEmail)

        call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("HandleSettings", response.body().toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("HandleSettings", "Failed to update email: ${t.message}")
            }
        })
    }

    fun deleteAccount(username: String){
        val call = settingsAPI.deleteAccount(username)

        call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("HandleSettings", response.body().toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("HandleSettings", "Failed to delete user: ${t.message}")
            }
        })
    }

    // Security and Privacy
    fun saveAuthKeyToDatabase(username: String, key: String) {
        val encodedKey = URLEncoder.encode(key)
        val call = biometricAPI.saveAuthKeyToDatabase(username, encodedKey)

        call.enqueue(object: Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("HandleSettings", response.body().toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("HandleSettings", "Failed to save auth key: ${t.message}")
            }
        })
    }

    fun validateBiometricAuth(authKey: String, callback: HandleBiometricAuthentication.BiometricAuthCallback) {
        val encodedKey = URLEncoder.encode(authKey)
        val call = biometricAPI.authBiometric(encodedKey)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("HandleSettings", "Biometric authentication validated successfully")
                    callback.onBiometricAuthSuccess()
                } else {
                    Log.d("HandleSettings", "Biometric authentication validation failed")
                    callback.onBiometricAuthError(-1, "Invalid biometric authentication")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("HandleSettings", "Failed to authenticate user: ${t.message}")
                callback.onBiometricAuthError(-1, "Failed to authenticate user: ${t.message}")
            }
        })
    }

    // Utils
    fun getUsernameByKey(authKey: String, callback: HandleBiometricAuthentication.UsernameCallback) {
        val encodedKey = URLEncoder.encode(authKey, "UTF-8")
        val call = biometricAPI.getUsernameByKey(encodedKey)

        call.enqueue(object : Callback<UsernameResponse> {
            override fun onResponse(call: Call<UsernameResponse>, response: Response<UsernameResponse>) {
                if (response.isSuccessful) {
                    val usernameResponse = response.body()
                    if (usernameResponse != null) {
                        val username = usernameResponse.username
                        Log.d("LoginActivity", "Username received: $username")
                        callback.onUsernameReceived(username)
                        return
                    }
                } else {
                    Log.d("LoginActivity", "Response unsuccessful: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UsernameResponse>, t: Throwable) {
                Log.d("LoginActivity", "Failed to get username: ${t.message}")
            }
        })
    }

}