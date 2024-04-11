package com.example.budgetbuddy.Utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.example.budgetbuddy.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PingListener {
    fun onPingSuccess(responseCode: Int)
    fun onPingFailure(exception: Exception)
}

interface PingService {
    @GET("/")
    suspend fun ping(): ResponseBody
}

class BackendPinger (private val pingListener: PingListener) {

    private val pingIntervalMillis = 5000
    private var job: Job? = null
    private var isActive = false
    private val retrofit = RetrofitUtils.initRetrofit()
    private val pingService: PingService = retrofit.create(PingService::class.java)

    fun start() {
        if (!isActive) {
            isActive = true
            job = CoroutineScope(Dispatchers.IO).launch {
                while (isActive) {
                    ping()
                    delay(pingIntervalMillis.toLong())
                }
            }
        }
    }

    fun stop() {
        isActive = false
        job?.cancel()
    }

    private suspend fun ping() {
        try {
            val response = pingService.ping()

            if (response != null) {
                pingListener.onPingSuccess(200)
            }
        } catch (e: Exception) {
            pingListener.onPingFailure(e)
            showErrorDialog((pingListener as Activity))
        }
    }

    private fun showErrorDialog(context: Context) {
        Handler(Looper.getMainLooper()).post {
            val dialogBuilder = AlertDialog.Builder(context)
            val dialogView = LayoutInflater.from(context).inflate(R.layout.server_down, null)
            dialogBuilder.setView(dialogView)
            val alertDialog = dialogBuilder.create()

            alertDialog.setCancelable(false)
            alertDialog.setCanceledOnTouchOutside(false)

            val exitButton = dialogView.findViewById<Button>(R.id.exit_button)
            val spinner = dialogView.findViewById<ProgressBar>(R.id.progressBar)


            exitButton.setOnClickListener {
                (context as Activity).finish()
            }

            alertDialog.show()
        }
    }
}