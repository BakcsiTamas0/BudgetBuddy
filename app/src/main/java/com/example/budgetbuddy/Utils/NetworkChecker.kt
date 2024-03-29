package com.example.budgetbuddy.Utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.example.budgetbuddy.R

class NetworkChecker() {

    fun checkConnectivity(context: Context) {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetwork

        if (activeNetwork == null) {
            val dialogBuilder = AlertDialog.Builder(context)
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_spinner, null)
            dialogBuilder.setView(dialogView)
            val alertDialog = dialogBuilder.create()

            alertDialog.setTitle("No Internet Connection")
            alertDialog.setCancelable(false)
            alertDialog.setCanceledOnTouchOutside(false)

            val retryButton = dialogView.findViewById<Button>(R.id.retry_button)
            val exitButton = dialogView.findViewById<Button>(R.id.exit_button)
            val spinner = dialogView.findViewById<ProgressBar>(R.id.progressBar)

            retryButton.setOnClickListener {
                spinner.visibility = View.VISIBLE

                retryButton.isEnabled = false
                exitButton.isEnabled = false

                checkConnectivityWithRetry(context) { isConnected ->
                    if (isConnected) {
                        alertDialog.dismiss()
                    } else {
                        Toast.makeText(context, "Still no internet connection", Toast.LENGTH_SHORT).show()
                        spinner.visibility = View.GONE
                        retryButton.isEnabled = true
                        exitButton.isEnabled = true
                    }
                }
            }

            exitButton.setOnClickListener {
                (context as? Activity)?.finish()
            }

            alertDialog.show()
        }
    }

    private fun checkConnectivityWithRetry(context: Context, callback: (Boolean) -> Unit) {
        Handler().postDelayed({
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = manager.activeNetwork
            val isConnected = activeNetwork != null
            callback(isConnected)
        }, 2000)
    }

}