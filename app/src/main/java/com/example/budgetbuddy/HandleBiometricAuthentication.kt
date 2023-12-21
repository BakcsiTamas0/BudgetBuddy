package com.example.budgetbuddy

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class HandleBiometricAuthentication(private var context: AppCompatActivity) {
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var executor: Executor

    private val biometricCallback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            Log.e("TAG", "Authentication error: $errString")
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            Log.d("TAG", "Authentication succeeded!")
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            Log.d("TAG", "Authentication failed!")
        }
    }

    init {
        initBiometric()
    }

    private fun initBiometric() {
        executor = ContextCompat.getMainExecutor(context)
        biometricPrompt = createBiometricPrompt()
        promptInfo = createPromptInfo()
    }

    private fun createBiometricPrompt(): BiometricPrompt {
        return BiometricPrompt(context, executor, biometricCallback)
    }

    private fun createPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Use your fingerprint or face to continue")
            .setNegativeButtonText("Cancel")
            .build()
    }

    fun showBiometricPrompt() {
        biometricPrompt.authenticate(promptInfo)
    }
}
