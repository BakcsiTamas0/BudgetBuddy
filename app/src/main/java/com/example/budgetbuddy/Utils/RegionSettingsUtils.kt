package com.example.budgetbuddy.Utils

import android.widget.ImageView
import android.widget.TextView
import com.example.budgetbuddy.DataClasses.ExchangeData.ExchangeItem
import com.example.budgetbuddy.R

class RegionSettingsUtils {

    fun setFullName(textView: TextView, item: ExchangeItem): Any {
        return when (item.currency) {
            "RON" -> textView.text = "Romanian Leu"
            "CAD" -> textView.text = "Canadian Dollar"
            "CHF" -> textView.text = "Swiss Franc"
            "CNY" -> textView.text = "Chinese Yuan"
            "CZK" -> textView.text = "Czech Republic Koruna"
            "DKK" -> textView.text = "Danish Krone"
            "EUR" -> textView.text = "Euro"
            "GBP" -> textView.text = "British Pound Sterling"
            "HRK" -> textView.text = "Croatian Kuna"
            "HUF" -> textView.text = "Hungarian Forint"
            "JPY" -> textView.text = "Japanese Yen"
            "MDL" -> textView.text = "Moldovan Leu"
            "MXN" -> textView.text = "Mexican Peso"
            "NOK" -> textView.text = "Norwegian Krone"
            "PLN" -> textView.text = "Polish Złoty"
            "RUB" -> textView.text = "Russian Ruble"
            "SEK" -> textView.text = "Swedish Krona"
            "TRY" -> textView.text = "Turkish Lira"
            "USD" -> textView.text = "United States Dollar"
            else -> item.currency
        }
    }

     fun setCountryImage(imageView: ImageView, item: ExchangeItem) {
        return when (item.currency) {
            "RON" -> imageView.setImageResource(R.drawable.romania_flag)
            "CAD" -> imageView.setImageResource(R.drawable.canada_flag)
            "CHF" -> imageView.setImageResource(R.drawable.switzernald_flag)
            "CNY" -> imageView.setImageResource(R.drawable.china_flag)
            "CZK" -> imageView.setImageResource(R.drawable.czech_flag)
            "DKK" -> imageView.setImageResource(R.drawable.denmark_flag)
            "EUR" -> imageView.setImageResource(R.drawable.europe_flag)
            "GBP" -> imageView.setImageResource(R.drawable.united_kingdom_flag)
            "HRK" -> imageView.setImageResource(R.drawable.croatia_flag)
            "HUF" -> imageView.setImageResource(R.drawable.hungary_flag)
            "JPY" -> imageView.setImageResource(R.drawable.japan_flag)
            "NOK" -> imageView.setImageResource(R.drawable.norway_flag)
            "PLN" -> imageView.setImageResource(R.drawable.poland_flag)
            "RUB" -> imageView.setImageResource(R.drawable.russia_flag)
            "SEK" -> imageView.setImageResource(R.drawable.sweden_flag)
            "TRY" -> imageView.setImageResource(R.drawable.turkey_flag)
            "USD" -> imageView.setImageResource(R.drawable.usa_flag)
            else -> imageView.setImageResource(R.drawable.profit)

        }
    }

    fun setFullCountryName(item: ExchangeItem): String {
        return when (item.currency) {
            "RON" -> "Romania"
            "CAD" -> "Canada"
            "CHF" -> "Switzerland"
            "CNY" -> "China"
            "CZK" -> "Czech Republic"
            "DKK" -> "Denmark"
            "GBP" -> "United Kingdom"
            "HRK" -> "Croatia"
            "HUF" -> "Hungary"
            "JPY" -> "Japan"
            "NOK" -> "Norway"
            "PLN" -> "Poland"
            "RUB" -> "Russia"
            "SEK" -> "Sweden"
            "TRY" -> "Turkey"
            "USD" -> "United States"
            else -> item.currency
        }
    }
}
