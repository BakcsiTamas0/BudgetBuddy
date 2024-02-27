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

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UpdateEmailFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1).toString()
        }
    }

    private val handleSettings = HandleSettings()

    private lateinit var username: String

    private lateinit var currentEmail: TextView
    private lateinit var newEmailET: EditText
    private lateinit var updateEmailButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_update_email, container, false)

        currentEmail = view.findViewById(R.id.currentEmail)
        newEmailET = view.findViewById(R.id.newEmail)
        updateEmailButton = view.findViewById(R.id.updateEmailSaveButton)

        val newEmail = newEmailET.text.toString()

        updateEmailButton.setOnClickListener() {
            handleSettings.updateEmail(username, newEmail)
            parentFragmentManager.popBackStack()
        }


        return view}

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            UpdateEmailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                }
            }
    }
}