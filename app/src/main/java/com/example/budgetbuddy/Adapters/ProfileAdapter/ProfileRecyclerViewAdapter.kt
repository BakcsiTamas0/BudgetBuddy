package com.example.budgetbuddy.Adapters.ProfileAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.Handlers.HandleProfileCRUD
import com.example.budgetbuddy.R

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
        return itemList.size
    }

    inner class ViewHolder(itemView: View, private val username: String) : RecyclerView.ViewHolder(itemView) {
        private val userCounter: TextView = itemView.findViewById(R.id.addedUserNumber)
        private val addedUserName: TextView = itemView.findViewById(R.id.addedUserName)
        private val saveExtraProfileUserButton: Button = itemView.findViewById(R.id.addedUserSaveButton)
        private val  HandleSubUserCRUD = HandleProfileCRUD(context)
        fun bind(item: String) {
            userCounter.text = (itemList.indexOf(item) + 1).toString()
            addedUserName.text = item

            val subUsername = addedUserName.text.toString()

            saveExtraProfileUserButton.setOnClickListener {
                HandleSubUserCRUD.saveSubUser(username, subUsername)
            }
        }
    }
}