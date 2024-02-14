package com.example.budgetbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.Adapters.ProfileAdapter.ProfileRecyclerViewAdapter
import com.example.budgetbuddy.DataClasses.ProfileData.ProfileDataResponse
import com.example.budgetbuddy.Handlers.ProfileHandler.HandleProfileCRUD

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileUsername: TextView
    private lateinit var profileEmail: TextView
    private lateinit var addNewUserButton: Button
    private lateinit var addUserRecyclerView: RecyclerView
    private lateinit var handleProfileCRUD: HandleProfileCRUD
    private lateinit var subUserList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileUsername = findViewById(R.id.profileUsername)
        profileEmail = findViewById(R.id.profileEmailAddress)
        addNewUserButton = findViewById(R.id.addNewUserButton)
        addUserRecyclerView = findViewById(R.id.addUserRecyclerView)
        handleProfileCRUD = HandleProfileCRUD(this)

        profileUsername.text = intent.getStringExtra("USERNAME")
        profileEmail.text = intent.getStringExtra("EMAIL")

        subUserList = mutableListOf()

        val customRecyclerViewAdapter = ProfileRecyclerViewAdapter(this, subUserList, profileUsername.text.toString())
        addUserRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        addUserRecyclerView.setHasFixedSize(true)

        addUserRecyclerView.adapter = customRecyclerViewAdapter

        addNewUserButton.setOnClickListener {
            val newItem = "User ${customRecyclerViewAdapter.itemCount + 1}"
            customRecyclerViewAdapter.addItem(newItem)
            addUserRecyclerView.scrollToPosition(customRecyclerViewAdapter.itemCount - 1)
        }

        handleProfileCRUD.getSubUsers(profileUsername.text.toString(), object : HandleProfileCRUD.OnSubUserDataReceiver {
            override fun onSubUserDataReceived(profileDataResponse: ProfileDataResponse) {
                val subUsernames = profileDataResponse.response

                subUserList.clear()

                for (subUser in subUsernames) {
                    subUserList.add(subUser)
                }

                customRecyclerViewAdapter.notifyDataSetChanged()
            }
        })
    }
}
