package com.example.budgetbuddy.Adapters.ProfileAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.Handlers.ProfileHandler.HandleProfileCRUD
import com.example.budgetbuddy.R
import kotlin.math.min

class ProfileRecyclerViewAdapter(
    private val context: Context,
    private val itemList: MutableList<String>,
    private val username: String
) : RecyclerView.Adapter<ProfileRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_profile_extra_user_recyclerview_item, parent, false)
        return ViewHolder(view, username)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return min(5, itemList.size)
    }

    fun addItem(item: String) {
        if (itemList.size < 5) {
            itemList.add(item)
            notifyItemInserted(itemList.size - 1)
        } else {
            Toast.makeText(context, "Only 5 other users can be added!", Toast.LENGTH_SHORT).show()}
    }

    inner class ViewHolder(itemView: View, private val username: String) : RecyclerView.ViewHolder(itemView) {
        private val userCounter: TextView = itemView.findViewById(R.id.addedUserNumber)
        private val addedUserName: EditText = itemView.findViewById(R.id.addedUserName)
        private val deleteExtraProfileUserButton: Button = itemView.findViewById(R.id.addedUserDeleteButton)
        private val handleSubUserCRUD = HandleProfileCRUD(context)

        fun bind(item: String) {
            userCounter.text = (itemList.indexOf(item) + 1).toString()
            addedUserName.setText(item)

            addedUserName.setOnFocusChangeListener { _, hasFocus ->
                val subUsername = addedUserName.text.toString()

                if (!hasFocus) {
                    handleSubUserCRUD.saveSubUser(username, subUsername)
                }
            }

            deleteExtraProfileUserButton.setOnClickListener {
                handleSubUserCRUD.deleteSubUsers(username, addedUserName.text.toString())
                Toast.makeText(context, "User ${addedUserName.text} deleted successfully!", Toast.LENGTH_SHORT).show()

                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemList.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }
}