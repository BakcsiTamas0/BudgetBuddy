package com.example.budgetbuddy.Adapters.SettingsAdapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.DataClasses.SettingsData.SettingsItem
import com.example.budgetbuddy.Fragments.RegionSettings.RegionSettingsFragment
import com.example.budgetbuddy.Fragments.Settings.BiometricAuthenticationFragment
import com.example.budgetbuddy.Fragments.Settings.DeleteAccountFragment
import com.example.budgetbuddy.Fragments.Settings.UpdateEmailFragment
import com.example.budgetbuddy.Fragments.Settings.UpdatePasswordFragment
import com.example.budgetbuddy.Fragments.Settings.UpdateUsernameFragment
import com.example.budgetbuddy.R

class SettingsAdapter(
    private val context: Context,
    private val settingItems: MutableList<SettingsItem>,
    private val username: String,
    private val onImageUpload: (username: String, image: Uri) -> Unit
) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {

    inner class SettingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.settingsTitleTextView)
        val subSettingsRecyclerView: RecyclerView = itemView.findViewById(R.id.subSettingsRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_settings_item, parent, false)
        return SettingsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return settingItems.size
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val settingItem = settingItems[position]

        holder.titleTextView.text = settingItem.title

        if (settingItem.subSettings.isNotEmpty()) {
            holder.titleTextView.setOnClickListener {
                toggleSubSettingsVisibility(holder)
            }

            val subSettingsAdapter = SubSettingsAdapter(
                context,
                settingItem.subSettings,
                onItemClickListener = { subSetting ->
                    handleSubSettingClick(settingItem, subSetting)
                }
            )

            holder.subSettingsRecyclerView.layoutManager = LinearLayoutManager(context)
            holder.subSettingsRecyclerView.adapter = subSettingsAdapter
        } else {
            holder.subSettingsRecyclerView.visibility = View.GONE
        }
    }

    private fun toggleSubSettingsVisibility(holder: SettingsViewHolder) {
        holder.subSettingsRecyclerView.visibility =
            if (holder.subSettingsRecyclerView.visibility == View.VISIBLE) {
                View.GONE
            } else {
                View.VISIBLE
            }
    }

    private fun handleSubSettingClick(settingsItem: SettingsItem, subSetting: String) {
        when (settingsItem.title) {
            "Account" -> {
                when (subSetting) {
                    "Change username" -> {
                        val updateUsernameFragment = UpdateUsernameFragment.newInstance(username)
                        val fragmentTransaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        fragmentTransaction.replace(android.R.id.content, updateUsernameFragment)
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    }

                    "Change password" -> {
                        val updatePasswordFragment = UpdatePasswordFragment.newInstance(username)
                        val fragmentTransaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        fragmentTransaction.replace(android.R.id.content, updatePasswordFragment)
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    }

                    "Change email" -> {
                        val updateEmailFragment = UpdateEmailFragment.newInstance(username)
                        val fragmentTransaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        fragmentTransaction.replace(android.R.id.content, updateEmailFragment)
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    }

                    "Update region and currency" -> {
                        val updateRegionSettingsFragment = RegionSettingsFragment.newInstance(username)
                        val fragmentTransaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        fragmentTransaction.replace(android.R.id.content, updateRegionSettingsFragment)
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    }

                    "Delete account" -> {
                        val deleteAccountFragment = DeleteAccountFragment.newInstance(username)
                        val fragmentTransaction= (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        fragmentTransaction.replace(android.R.id.content, deleteAccountFragment)
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    }
                }
            }
            "Privacy and security" -> {
                when (subSetting) {
                    "Biometric authentication" -> {
                        val biometricAuthenticationFragment = BiometricAuthenticationFragment.newInstance(username)
                        val fragmentTransaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        fragmentTransaction.replace(android.R.id.content, biometricAuthenticationFragment)
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    }
                }
            }

            "Log out" -> {
            }
        }
    }
}