package com.example.budgetbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.Adapters.SettingsAdapter.SettingsAdapter
import com.example.budgetbuddy.DataClasses.SettingsData.SettingsItem
import com.example.budgetbuddy.Fragments.Settings.UpdateUsernameFragment
import com.example.budgetbuddy.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var settings: MutableList<SettingsItem>
    private lateinit var adapter: SettingsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("USERNAME").toString()

        settings = mutableListOf<SettingsItem>(
            SettingsItem("Account", listOf("Update profile picture", "Change username", "Change password", "Change email", "Update region and currency", "Delete account")),
            SettingsItem("Notifications", listOf("Push notifications", "Email notifications")),
            SettingsItem("Privacy and security",),
            SettingsItem("General",),
            SettingsItem("Log out"),
        )

        adapter = SettingsAdapter(
            this,
            settings,
            username,
            onImageUpload = { username, image -> "valami"; "valami"})
        recyclerView = findViewById(R.id.settingsRecyclerView)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}