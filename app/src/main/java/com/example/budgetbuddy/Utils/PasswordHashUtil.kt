package com.example.budgetbuddy.Utils

import java.security.MessageDigest

class PasswordHashUtil {
    companion object {
        fun hashPassword(password:String):String {
            return hash(password)
        }

        private fun hash(password:String):String{
            val md = MessageDigest.getInstance("SHA-256")
            val hashBytes = md.digest(password.toByteArray())
            return toHexString(hashBytes)
        }

        private fun toHexString(bytes:ByteArray):String{
            return bytes.joinToString("") { "%02x".format(it) }
        }
    }
}