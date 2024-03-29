package com.example.budgetbuddy.Adapters.UserRegionSettingsAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.budgetbuddy.DataClasses.ExchangeData.ExchangeItem
import com.example.budgetbuddy.R
import com.example.budgetbuddy.Utils.RegionSettingsUtils

class UserRegionSettingsCurrencySpinnerAdapter(
    context: Context,
    currencyList: List<ExchangeItem>
): ArrayAdapter<ExchangeItem>(context, android.R.layout.simple_spinner_item, currencyList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private lateinit var regionSettingsFullCurrencyName: TextView
    private lateinit var regionSettingsAbbrCurrencyName: TextView
    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = convertView?: inflater.inflate(R.layout.custom_region_setting_currency_spinner_item, parent, false)

        val regionUtils = RegionSettingsUtils()

        regionSettingsFullCurrencyName = rowView.findViewById(R.id.regionSettingsFullCurrencyName)
        regionSettingsAbbrCurrencyName = rowView.findViewById(R.id.regionSettingsAbbrCurrencyName)

        val currentItem = getItem(position)

        if (currentItem != null) {
            val fullCountryName = regionUtils.setFullCountryName(currentItem)
            regionSettingsFullCurrencyName.text = fullCountryName.toString()
            regionSettingsAbbrCurrencyName.text = currentItem.currency
        }


        return rowView
    }
}