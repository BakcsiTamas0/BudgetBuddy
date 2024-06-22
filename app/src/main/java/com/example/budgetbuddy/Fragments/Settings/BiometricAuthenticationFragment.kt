package com.example.budgetbuddy.Fragments.Settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.example.budgetbuddy.BiometricAuthentication.HandleBiometricAuthentication
import com.example.budgetbuddy.Handlers.Settings.HandleSettings
import com.example.budgetbuddy.R
import com.example.budgetbuddy.Utils.BiometricAuthenticationUtils

private const val ARG_PARAM1 = "username"

class BiometricAuthenticationFragment : Fragment(), HandleBiometricAuthentication.BiometricAuthCallback {
    private lateinit var username: String
    private lateinit var setupBiometricAuthenticationButton: TextView
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var settingsHandler: HandleSettings
    private lateinit var biometricAuthentication: HandleBiometricAuthentication

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_biometric_authentication, container, false)

        setupBiometricAuthenticationButton = view.findViewById(R.id.setupBiometricAuthentication)

        sharedPreferences = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        settingsHandler = HandleSettings()
        biometricAuthentication = HandleBiometricAuthentication(requireActivity() as FragmentActivity)

        setupBiometricAuthenticationButton.setOnClickListener {
            generateAndSavePublicKey()
        }
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateAndSavePublicKey() {
        val biometricUtils = BiometricAuthenticationUtils()
        val publicKey = biometricUtils.generateKeyPair()

        username = arguments?.getString(ARG_PARAM1).toString()
        settingsHandler.saveAuthKeyToDatabase(username, publicKey)

        Log.d("BiometricFragment", "Generated and saved public key")
        saveBiometricPreference(true)
        biometricAuthentication.showBiometricPrompt()
    }

    override fun onBiometricAuthSuccess() {
        saveBiometricPreference(true)

        val navController = findNavController()
        navController.navigateUp()
    }

    override fun onBiometricAuthError(errorCode: Int, errString: CharSequence) {
        Toast.makeText(requireContext(), "Biometric authentication error: $errString", Toast.LENGTH_SHORT).show()
    }

    private fun saveBiometricPreference(isBiometricEnabled: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("biometric_enabled", isBiometricEnabled)
        editor.apply()

        Log.d("BiometricFragment", "Biometric preference saved: $isBiometricEnabled")
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            BiometricAuthenticationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                }
            }
    }
}
