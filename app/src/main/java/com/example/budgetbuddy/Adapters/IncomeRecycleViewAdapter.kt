package com.example.budgetbuddy.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.DataClasses.IncomeItem
import com.example.budgetbuddy.R

class IncomeRecycleViewAdapter(private val context: Context, private val itemList: MutableList<IncomeItem>) :
    RecyclerView.Adapter<IncomeRecycleViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_income_recycle_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.incomeTypeImage)
        private val textView: TextView = itemView.findViewById(R.id.incomeTypeList)
        private val amountView: TextView = itemView.findViewById(R.id.incomeAmount)

        fun bind(item: IncomeItem) {
            imageView.setImageResource(item.imageId)
            amountView.text = String.format("%.2f", item.amount)
            textView.text = item.text
        }
    }
}
