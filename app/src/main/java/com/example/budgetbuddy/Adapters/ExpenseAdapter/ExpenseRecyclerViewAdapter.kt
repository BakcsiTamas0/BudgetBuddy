package com.example.budgetbuddy.Adapters.ExpenseAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.DataClasses.ExpenseData.ExpenseItem
import com.example.budgetbuddy.Handlers.FiancesHandler.HandleExpenseCRUD
import com.example.budgetbuddy.R

class ExpenseRecyclerViewAdapter(
    private val context: Context,
    private val itemList: MutableList<ExpenseItem>,
    private val username: String) :
    RecyclerView.Adapter<ExpenseRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_expense_recycler_view_item, parent, false)
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
        private val imageView: ImageView = itemView.findViewById(R.id.expenseTypeImage)
        private val textView: TextView = itemView.findViewById(R.id.expenseTypeList)
        private val amountView: TextView = itemView.findViewById(R.id.expenseAmount)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteExpenseItemBtn)
        private val handleExpenseCRUD = HandleExpenseCRUD(context)

        init{
            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = itemList[position]
                    itemList.removeAt(position)
                    notifyItemRemoved(position)

                    handleExpenseCRUD.deleteExpenseData(username, item.text, item.amount!!.toDouble())
                }
            }
        }

        fun bind(item: ExpenseItem) {
            imageView.setImageResource(item.imageId)
            amountView.text = String.format("%.2f", item.amount)
            textView.text = item.text
        }
    }
}
