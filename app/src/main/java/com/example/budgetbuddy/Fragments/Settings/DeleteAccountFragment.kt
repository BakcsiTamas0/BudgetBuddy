package com.example.budgetbuddy.Fragments.Settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import com.example.budgetbuddy.Handlers.Settings.HandleSettings
import com.example.budgetbuddy.LoginActivity
import com.example.budgetbuddy.R

private const val ARG_PARAM1 = "param1"

class DeleteAccountFragment : Fragment() {
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1).toString()
        }
    }

    private var handleSettings = HandleSettings()

    private lateinit var username: String

    private lateinit var confirmationCheckbox: CheckBox
    private lateinit var deleteUserButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View =  inflater.inflate(R.layout.fragment_delete_user, container, false)

        confirmationCheckbox = view.findViewById(R.id.deleteConfirmationCheckbox)
        deleteUserButton = view.findViewById(R.id.deleteUserSaveButton)

        deleteUserButton.setOnClickListener {
            if (confirmationCheckbox.isChecked) {
                handleSettings.deleteAccount(username)

                val intent = Intent(this.context, LoginActivity::class.java)
                startActivity(intent)

                activity?.finish()}
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            DeleteAccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                }
            }
    }
}