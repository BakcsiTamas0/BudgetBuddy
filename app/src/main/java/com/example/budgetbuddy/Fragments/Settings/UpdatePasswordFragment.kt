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
import com.example.budgetbuddy.Utils.PasswordHashUtil
import com.example.budgetbuddy.Utils.PasswordHashUtil.Companion.hashPassword

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UpdatePasswordFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1).toString()
        }
    }

    private var handleSettings = HandleSettings()
    private var hashUtils = PasswordHashUtil()

    private lateinit var username: String

    private lateinit var currentPassword: TextView
    private lateinit var newPasswordET: EditText
    private lateinit var updatePasswordSaveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_update_password, container, false)

        newPasswordET = view.findViewById(R.id.newPassword)
        updatePasswordSaveButton = view.findViewById(R.id.updatePasswordSaveButton)

        updatePasswordSaveButton.setOnClickListener {
            val newPassword = newPasswordET.text.toString()
            val hashedPassword = hashPassword(newPassword)

            handleSettings.updatePassword(username, hashedPassword)
            parentFragmentManager.popBackStack()
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            UpdatePasswordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                }
            }
    }
}