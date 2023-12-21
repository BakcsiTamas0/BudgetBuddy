package com.example.budgetbuddy.BiometricAuthentication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.budgetbuddy.R

class BiometricAuthActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var buttonEnableBiometric: Button
    private lateinit var buttonCancelBiometric: Button
    private lateinit var biometricAuthentication: HandleBiometricAuthentication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biometric)

        sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        biometricAuthentication = HandleBiometricAuthentication(this)

        buttonEnableBiometric = findViewById(R.id.buttonEnableBiometric)
        buttonCancelBiometric = findViewById(R.id.buttonCancelBiometric)

        buttonEnableBiometric.setOnClickListener {
            biometricAuthentication.showBiometricPrompt()
            saveBiometricPreference(true)
            finish()
        }

        buttonCancelBiometric.setOnClickListener {
            saveBiometricPreference(false)
            finish()
        }
    }

    private fun saveBiometricPreference(isBiometricEnabled: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("biometric_enabled", isBiometricEnabled)
        editor.apply()
    }
}
