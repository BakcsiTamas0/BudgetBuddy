package com.example.budgetbuddy.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.budgetbuddy.DataClasses.IncomeItem
import com.example.budgetbuddy.R

class CustomIncomeSpinnerAdapter(
    context: Context,
    private val items: List<IncomeItem>,
) : ArrayAdapter<IncomeItem>(context, android.R.layout.simple_spinner_item, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = convertView ?: inflater.inflate(R.layout.custom_income_spinner_item, parent, false)

        val textView: TextView = rowView.findViewById(R.id.incomeType)
        val imageView: ImageView = rowView.findViewById(R.id.incomeImage)

        val currentItem = getItem(position)

        if (currentItem != null) {
            textView.text = currentItem.text
            imageView.setImageResource(currentItem.imageId)
        }

        return rowView
    }
}

