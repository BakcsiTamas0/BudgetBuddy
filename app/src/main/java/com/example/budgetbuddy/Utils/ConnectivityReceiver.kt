package com.example.budgetbuddy.Utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class ConnectivityReceiver(private val listener: ConnectivityChangeListener) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activatedNetwork = manager.activeNetwork
            val isConnected = activatedNetwork != null
            listener.onConnectivityChange(isConnected)
        }
    }

    interface ConnectivityChangeListener {
        fun onConnectivityChange(isConnected: Boolean)
    }
}