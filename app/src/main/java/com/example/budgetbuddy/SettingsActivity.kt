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
            "username",
            onImageUpload = { username, image -> "valami"; "valami"})
        recyclerView = findViewById(R.id.settingsRecyclerView)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun handleSettingsItemClick(settingsItem: SettingsItem) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        when (settingsItem.title) {
            "Account" -> {
                when (settingsItem.subSettings.toString()) {
                    "Update profile picture" -> {
                        showUpdateProfilePictureDialog()
                    }
                    "Change username" -> {
                        Toast.makeText(this, "Change username", Toast.LENGTH_SHORT).show()
                        val updateUsernameFragment = UpdateUsernameFragment.newInstance("Lajoska")
                        fragmentTransaction.replace(android.R.id.content, updateUsernameFragment)
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    }
                    "Change password" -> {
                        // Handle Change password item click
                    }
                    "Change email" -> {
                        // Handle Change email item click
                    }

                }
            }
            "Notifications" -> {
                // Handle Notifications item click
            }
            "Privacy and security" -> {
                // Handle Privacy and security item click
            }
            "General" -> {
                // Handle General item click
            }
            "Notification" -> {
                // Handle Notification item click
            }
            "Data management" -> {
                // Handle Data management item click
            }
            "Log out" -> {
                // Handle Log out item click
            }
        }
    }

    private fun showUpdateProfilePictureDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Update profile picture")
        builder.setMessage("Choose a method to update your profile picture")

        builder.setPositiveButton("Select from Gallery") {_, _, ->
        }

        builder.setNegativeButton("Cancel") {_, _, ->
            // Do nothing
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}