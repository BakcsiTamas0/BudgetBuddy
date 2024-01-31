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
        private val exchangeImageView: ImageView = itemView.findViewById(R.id.exchangeImageView)
        private val exchangeAbbreviation: TextView =
            itemView.findViewById(R.id.exchangeAbbreviation)
        private val exchangeFullName: TextView = itemView.findViewById(R.id.exchangeFullName)
        private val exchangeBuyPrice: TextView = itemView.findViewById(R.id.exchangeRate)

        fun bind(item: ExchangeItem) {
            exchangeAbbreviation.text = item.currency
            exchangeBuyPrice.text = item.rate.toString()
            setFullName(ExchangeItem(item.currency, item.rate))
            setCountryImage(ExchangeItem(item.currency, item.rate))
        }

        private fun setFullName(item: ExchangeItem) {
            when (item.currency) {
                "RON" -> {
                    exchangeFullName.text = "Romanian Leu"
                }

                "CAD" -> {
                    exchangeFullName.text = "Canadian Dollar"
                }

                "CHF" -> {
                    exchangeFullName.text = "Swiss Franc"
                }

                "CNY" -> {
                    exchangeFullName.text = "Chinese Yuan"
                }

                "CZK" -> {
                    exchangeFullName.text = "Czech Republic Koruna"
                }

                "DKK" -> {
                    exchangeFullName.text = "Danish Krone"
                }

                "EUR" -> {
                    exchangeFullName.text = "Euro"
                }

                "GBP" -> {
                    exchangeFullName.text = "British Pound Sterling"
                }

                "HRK" -> {
                    exchangeFullName.text = "Croatian Kuna"
                }

                "HUF" -> {
                    exchangeFullName.text = "Hungarian Forint"
                }

                "JPY" -> {
                    exchangeFullName.text = "Japanese Yen"
                }

                "MDL" -> {
                    exchangeFullName.text = "Moldovan Leu"
                }

                "MXN" -> {
                    exchangeFullName.text = "Mexican Peso"
                }

                "NOK" -> {
                    exchangeFullName.text = "Norwegian Krone"
                }

                "PLN" -> {
                    exchangeFullName.text = "Polish Złoty"
                }

                "RUB" -> {
                    exchangeFullName.text = "Russian Ruble"
                }

                "SEK" -> {
                    exchangeFullName.text = "Swedish Krona"
                }

                "TRY" -> {
                    exchangeFullName.text = "Turkish Lira"
                }

                "USD" -> {
                    exchangeFullName.text = "United States Dollar"
                }

                else -> {
                    exchangeFullName.text = "Unknown Currency"
                }
            }
        }

        private fun setCountryImage(item: ExchangeItem) {
            when (item.currency) {
                "RON" -> {
                    exchangeImageView.setImageResource(R.drawable.romania_flag)
                }

                "CAD" -> {
                    exchangeImageView.setImageResource(R.drawable.canada_flag)
                }

                "CHF" -> {
                    exchangeImageView.setImageResource(R.drawable.switzernald_flag)
                }

                "CNY" -> {
                    exchangeImageView.setImageResource(R.drawable.china_flag)
                }

                "CZK" -> {
                    exchangeImageView.setImageResource(R.drawable.czech_flag)
                }

                "DKK" -> {
                    exchangeImageView.setImageResource(R.drawable.denmark_flag)
                }

                "EUR" -> {
                    exchangeImageView.setImageResource(R.drawable.europe_flag)
                }

                "GBP" -> {
                    exchangeImageView.setImageResource(R.drawable.united_kingdom_flag)
                }

                "HRK" -> {
                    exchangeImageView.setImageResource(R.drawable.croatia_flag)
                }

                "HUF" -> {
                    exchangeImageView.setImageResource(R.drawable.hungary_flag)
                }

                "JPY" -> {
                    exchangeImageView.setImageResource(R.drawable.japan_flag)
                }

                "NOK" -> {
                    exchangeImageView.setImageResource(R.drawable.norway_flag)
                }

                "PLN" -> {
                    exchangeImageView.setImageResource(R.drawable.poland_flag)
                }

                "RUB" -> {
                    exchangeImageView.setImageResource(R.drawable.russia_flag)
                }

                "SEK" -> {
                    exchangeImageView.setImageResource(R.drawable.sweden_flag)
                }

                "TRY" -> {
                    exchangeImageView.setImageResource(R.drawable.turkey_flag)
                }

                "USD" -> {
                    exchangeImageView.setImageResource(R.drawable.usa_flag)
                }

                else -> {
                    exchangeImageView.setImageResource(R.drawable.profit)
                }
            }
        }
    }
}