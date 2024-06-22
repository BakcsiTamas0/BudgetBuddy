package com.example.budgetbuddy.Fragments.Settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.budgetbuddy.Handlers.Settings.HandleSettings
import com.example.budgetbuddy.R

private const val ARG_PARAM1 = "username"

class UpdateUsernameFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1).toString()
        }
    }

    private var handleSettings = HandleSettings()

    private lateinit var username: String

    private lateinit var currentUsername: TextView
    private lateinit var newUsernameET: EditText
    private lateinit var usernameSaveButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_update_username, container, false)

        currentUsername = view.findViewById(R.id.currentUsername)
        newUsernameET = view.findViewById(R.id.newUsername)
        usernameSaveButton = view.findViewById(R.id.updateUsernameSaveButton)

        currentUsername.text = username

        usernameSaveButton.setOnClickListener {
            val newUsername = newUsernameET.text.toString()
            handleSettings.updateUsername(username, newUsername)
            parentFragmentManager.popBackStack()
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            UpdateUsernameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                }
            }
    }
}