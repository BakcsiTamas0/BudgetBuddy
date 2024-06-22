package com.example.budgetbuddy.BiometricAuthentication

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.budgetbuddy.Handlers.Settings.HandleSettings
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import java.security.PrivateKey
import java.security.Signature
import java.util.Base64
import java.util.concurrent.Executor

class HandleBiometricAuthentication(private var activity: FragmentActivity) {
    interface BiometricAuthCallback {
        fun onBiometricAuthSuccess()
        fun onBiometricAuthError(errorCode: Int, errString: CharSequence)
    }

    interface UsernameCallback {
        fun onUsernameReceived(username: String)
    }

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var executor: Executor

    private val biometricCallback = object : BiometricPrompt.AuthenticationCallback() {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            (activity as BiometricAuthCallback).onBiometricAuthSuccess()
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            (activity as BiometricAuthCallback).onBiometricAuthError(errorCode, errString)
        }

        override fun onAuthenticationFailed() {
            (activity as BiometricAuthCallback).onBiometricAuthError(-1, "Authentication failed")
        }
    }

    init {
        initBiometric()
    }

    private fun initBiometric() {
        executor = ContextCompat.getMainExecutor(activity)
        biometricPrompt = createBiometricPrompt()
        promptInfo = createPromptInfo()
    }

    private fun createBiometricPrompt(): BiometricPrompt {
        return BiometricPrompt(activity, executor, biometricCallback)
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
