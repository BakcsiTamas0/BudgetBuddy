package com.example.budgetbuddy

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.Adapters.SettingsAdapter.SettingsAdapter
import com.example.budgetbuddy.DataClasses.SettingsData.SettingsItem
import com.example.budgetbuddy.Fragments.RegionSettings.RegionSettingsFragment
import com.example.budgetbuddy.Fragments.Settings.UpdateUsernameFragment
import com.example.budgetbuddy.Utils.ConnectivityReceiver
import com.example.budgetbuddy.Utils.NetworkChecker
import com.example.budgetbuddy.databinding.ActivitySettingsBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn

class SettingsActivity : AppCompatActivity(),
    ConnectivityReceiver.ConnectivityChangeListener,
    RegionSettingsFragment.RegionSettingsListener
{
    private val connectivityReceiver = ConnectivityReceiver(this)
    private val networkChecker = NetworkChecker()

    private lateinit var settings: MutableList<SettingsItem>
    private lateinit var adapter: SettingsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username: String = intent.getStringExtra("USERNAME").toString()

        settings = mutableListOf<SettingsItem>(
            SettingsItem("Account",listOf("Update profile picture", "Change username", "Change password", "Change email", "Update region and currency", "Delete account")),
            SettingsItem("Notifications", listOf("Push notifications", "Email notifications")),
            SettingsItem("Privacy and security", listOf("Biometric authentication")),
            SettingsItem("General",),
            SettingsItem("Log out"),
        )

        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)
        val allSettings = mutableListOf<String>()

        adapter = SettingsAdapter(
            this,
            settings,
            username,
            onImageUpload = { username, image -> "valami"; "valami"})
        recyclerView = findViewById(R.id.settingsRecyclerView)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(connectivityReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(connectivityReceiver)
    }

    override fun onConnectivityChange(isConnected: Boolean) {
        if (!(isConnected)) {
            networkChecker.checkConnectivity(this)
        }
    }

    override fun onSaveUserRegionSettings() {
        saveUserRegionSettings()
    }

    private fun saveUserRegionSettings() {
        val sharedPref = getSharedPreferences("userRegionSettings", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("hasUserSetRegion", true)
            apply()
        }
    }
}