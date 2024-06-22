package com.example.budgetbuddy.Utils

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.util.Base64

class BiometricAuthenticationUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateKeyPair(): String {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore")

        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            "BudgetBuddyAuthKey",
            KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY)
            .setDigests(KeyProperties.DIGEST_SHA256)
            .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PSS)
            .setUserAuthenticationRequired(true)
            .setKeySize(2048)
            .build()

        keyPairGenerator.initialize(keyGenParameterSpec)
        val keyPair = keyPairGenerator.generateKeyPair()

        val publicKey = keyPair.public.encoded
        return Base64.getEncoder().encodeToString(publicKey)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPublicKey(): String? {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        val publicKey = keyStore.getCertificate("BudgetBuddyAuthKey").publicKey
        return Base64.getEncoder().encodeToString(publicKey.encoded)
    }
}
