package com.example.budgetbuddy.Adapters.DebtAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.DataClasses.DebtData.DebtItem
import com.example.budgetbuddy.Handlers.FiancesHandler.HandleDebtCRUD
import com.example.budgetbuddy.R

class DebtRecyclerViewAdapter (
    private val context: Context,
    private val itemList: MutableList<DebtItem>,
    private val username: String
) : RecyclerView.Adapter<DebtRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_debt_recycler_view_item, parent, false)
        return ViewHolder(view, username)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View, private val username: String) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.debtTypeImage)
        private val textView: TextView = itemView.findViewById(R.id.debtTypeList)
        private val amountView: TextView = itemView.findViewById(R.id.debtAmount)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteDebtItemBtn)
        private val handleDebtCRUD = HandleDebtCRUD(context)

        init{
            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = itemList[position]
                    itemList.removeAt(position)
                    notifyItemRemoved(position)

                    handleDebtCRUD.deleteDebtData(username, item.text, item.amount!!.toDouble())
                }
            }
        }

        fun bind(item: DebtItem) {
            imageView.setImageResource(item.imageId)
            amountView.text = String.format("%.2f", item.amount)
            textView.text = item.text
        }
    }
}
