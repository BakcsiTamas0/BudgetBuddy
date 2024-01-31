package com.example.budgetbuddy.Adapters.ExchangeAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.budgetbuddy.DataClasses.ExchangeData.ExchangeItem
import com.example.budgetbuddy.R

class ExchangeSpinnerAdapter(
    context: Context,
    items: List<ExchangeItem>
) : ArrayAdapter<ExchangeItem>(context, android.R.layout.simple_spinner_item, items){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private lateinit var exchangeFullName: TextView
    private lateinit var exchangeImageView: ImageView
    private lateinit var exchangeAbbreviation: TextView
    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = convertView ?: inflater.inflate(R.layout.custom_exchange_recycler_view_item, parent, false)

        exchangeAbbreviation = rowView.findViewById(R.id.exchangeAbbreviation)
        exchangeFullName = rowView.findViewById(R.id.exchangeFullName)
        exchangeImageView = rowView.findViewById(R.id.exchangeImageView)

        val currentItem = getItem(position)

        if (currentItem != null) {
            exchangeAbbreviation.text = currentItem.currency
            setFullName(ExchangeItem(currentItem.currency, currentItem.rate))
            setCountryImage(ExchangeItem(currentItem.currency, currentItem.rate))
        }

        return rowView
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
