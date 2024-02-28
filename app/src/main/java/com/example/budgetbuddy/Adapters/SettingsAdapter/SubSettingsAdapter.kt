package com.example.budgetbuddy.Adapters.SettingsAdapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.Utils.init

class SubSettingsAdapter(
    private val context: Context,
    private val subSettings: List<String>,
    private var onItemClickListener: (String) -> Unit
) : RecyclerView.Adapter<SubSettingsAdapter.SubSettingsViewHolder>() {

    inner class SubSettingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subSettingTextView: TextView = itemView.findViewById(R.id.subSettingTextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val subSetting = subSettings[position]
                    onItemClickListener(subSetting)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubSettingsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_sub_setting_item, parent, false)
        return SubSettingsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SubSettingsViewHolder, position: Int) {
        holder.subSettingTextView.text = subSettings[position]

        if (subSettings[position] == "Delete account") {
            holder.subSettingTextView.setTypeface(holder.subSettingTextView.getTypeface(), Typeface.BOLD)
            holder.subSettingTextView.setTextColor(Color.RED)
        }
    }

    override fun getItemCount(): Int {
        return subSettings.size
    }
}
