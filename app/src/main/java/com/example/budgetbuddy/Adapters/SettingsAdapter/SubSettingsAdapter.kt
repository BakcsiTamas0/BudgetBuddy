package com.example.budgetbuddy.Adapters.SettingsAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R

class SubSettingsAdapter(private val context: Context, private val subSettings: List<String>) :
    RecyclerView.Adapter<SubSettingsAdapter.SubSettingsViewHolder>() {

    inner class SubSettingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subSettingTextView: TextView = itemView.findViewById(R.id.subSettingTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubSettingsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_sub_setting_item, parent, false)
        return SubSettingsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SubSettingsViewHolder, position: Int) {
        holder.subSettingTextView.text = subSettings[position]
    }

    override fun getItemCount(): Int {
        return subSettings.size
    }
}
