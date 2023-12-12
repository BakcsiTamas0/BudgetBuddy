package com.example.budgetbuddy

import java.math.BigInteger
import java.security.MessageDigest
import java.security.SecureRandom
class PasswordHashUtil {
    companion object {
        private const val SALT_LENGTH = 16

        fun hashPassword(password:String):String{
            val salt = generateSalt()
            val hashedPassword = hashWithSalt(password, salt)
            return toHexString(salt + hashedPassword)
        }

        private fun generateSalt():ByteArray {
            val random = SecureRandom()
            val salt = ByteArray(SALT_LENGTH)
            random.nextBytes(salt)
            return salt
        }

        private fun hashWithSalt(password:String, salt:ByteArray):ByteArray{
            val md = MessageDigest.getInstance("SHA-256")
            md.update(salt)
            return md.digest(password.toByteArray())
        }

        private fun toHexString(bytes:ByteArray):String{
            return bytes.joinToString("") { "%02x".format(it) }
        }

        private fun verifyPassword(password:String, salt:ByteArray, hashedPassword:ByteArray):Boolean{
            val hashWithSalt = hashWithSalt(password, salt)
            return hashWithSalt.contentEquals(hashedPassword)
        }

    }
}