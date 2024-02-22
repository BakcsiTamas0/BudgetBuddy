package com.example.budgetbuddy.Adapters.ExchangeAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.DataClasses.ExchangeData.ExchangeItem
import com.example.budgetbuddy.R
import com.example.budgetbuddy.Utils.RegionSettingsUtils

class ExchangeRecyclerViewAdapter (
    private val context: Context,
    private val itemList: MutableList<ExchangeItem>):
    RecyclerView.Adapter<ExchangeRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_exchange_recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExchangeRecyclerViewAdapter.ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val regionUtils = RegionSettingsUtils()
        private val exchangeImageView: ImageView = itemView.findViewById(R.id.exchangeImageView)
        private val exchangeAbbreviation: TextView = itemView.findViewById(R.id.exchangeAbbreviation)
        private val exchangeFullName: TextView = itemView.findViewById(R.id.exchangeFullName)
        private val exchangeBuyPrice: TextView = itemView.findViewById(R.id.exchangeRate)

        fun bind(item: ExchangeItem) {
            exchangeAbbreviation.text = item.currency
            exchangeBuyPrice.text = String.format("%.2f", item.rate)
            regionUtils.setFullName(exchangeFullName, item)
            regionUtils.setCountryImage(exchangeImageView, item)
        }
    }
}