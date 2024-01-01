package com.example.budgetbuddy.Adapters.SettingsAdapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.DataClasses.SettingsData.SettingsItem
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

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val settingItem = settingItems[position]

        holder.titleTextView.text = settingItem.title

        if (settingItem.subSettings.isNotEmpty()) {
            holder.titleTextView.setOnClickListener {
                toggleSubSettingsVisibility(holder)
            }

            val subSettingsAdapter = SubSettingsAdapter(context, settingItem.subSettings)
            holder.subSettingsRecyclerView.layoutManager = LinearLayoutManager(context)
            holder.subSettingsRecyclerView.adapter = subSettingsAdapter
        } else {
            holder.subSettingsRecyclerView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return settingItems.size
    }

    private fun toggleSubSettingsVisibility(holder: SettingsViewHolder) {
        holder.subSettingsRecyclerView.visibility =
            if (holder.subSettingsRecyclerView.visibility == View.VISIBLE) {
                View.GONE
            } else {
                View.VISIBLE
            }
    }
}