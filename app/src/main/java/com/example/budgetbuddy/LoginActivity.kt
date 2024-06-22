package com.example.budgetbuddy

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.budgetbuddy.BiometricAuthentication.HandleBiometricAuthentication
import com.example.budgetbuddy.Handlers.Settings.HandleSettings
import com.example.budgetbuddy.Handlers.UserHandling.HandleLogin
import com.example.budgetbuddy.Handlers.UserHandling.HandleLogin.Companion.authenticateUser
import com.example.budgetbuddy.Handlers.UserHandling.HandleUserSignIn
import com.example.budgetbuddy.Utils.BackendPinger
import com.example.budgetbuddy.Utils.BiometricAuthenticationUtils
import com.example.budgetbuddy.Utils.ConnectivityReceiver
import com.example.budgetbuddy.Utils.CustomTextUtils
import com.example.budgetbuddy.Utils.NetworkChecker
import com.example.budgetbuddy.Utils.PingListener

class LoginActivity : AppCompatActivity(),
    HandleBiometricAuthentication.BiometricAuthCallback,
    ConnectivityReceiver.ConnectivityChangeListener,
    PingListener {

    private val connectivityReceiver = ConnectivityReceiver(this)
    private val networkChecker = NetworkChecker()
    private lateinit var backendPinger: BackendPinger

    private lateinit var customTextUtils: CustomTextUtils
    private lateinit var registerFromLogin: TextView

    private val PREFS_NAME = "preferences"
    private val PREF_USERNAME = "username"
    private val PREF_PASSWORD = "password"
    private val PREF_REMEMBER = "remember"

    private lateinit var appName: TextView

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var remember: CheckBox

    private lateinit var biometricAuthentication: HandleBiometricAuthentication
    private lateinit var settingsHandler: HandleSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        backendPinger = BackendPinger(this)
        backendPinger.start()

        customTextUtils = CustomTextUtils()

        appName = findViewById(R.id.appName)
        customTextUtils.applyLinearGradient(appName)

        username = findViewById(R.id.login_username)
        password = findViewById(R.id.login_password)
        remember = findViewById(R.id.remember)

        val loginBtn: Button = findViewById(R.id.login_btn)
        registerFromLogin = findViewById(R.id.registerFromLogin)
        customTextUtils.applyLinearGradient(registerFromLogin)

        val googleImage: ImageView = findViewById(R.id.googleImage)

        googleImage.setOnClickListener {
            val googleSignInIntent = Intent(this@LoginActivity, HandleUserSignIn::class.java)
            startActivity(googleSignInIntent)
        }

        loginBtn.setOnClickListener {
            authenticateUser(username.text.toString(), password.text.toString(), this, object : HandleLogin.AuthCallback {
                override fun onAuthSuccess() {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("USERNAME", username.text.toString())
                    startActivity(intent)
                }

                override fun onAuthError(errorCode: Int) {
                    Log.d("Error", "Login failed! Check your credentials and try again!")
                }
            })
            if (remember.isChecked) {
                savePreferences()
            } else {
                clearPreferences()
            }
        }

        registerFromLogin.setOnClickListener {
            val register = Intent(this, RegisterActivity::class.java)
            startActivity(register)
        }

        settingsHandler = HandleSettings()

        loadPreferences()
        checkBiometricPreference()
    }

    override fun onResume() {
        super.onResume()
        loadPreferences()
        checkBiometricPreference()
        registerReceiver(connectivityReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(connectivityReceiver)
    }

    override fun onConnectivityChange(isConnected: Boolean) {
        if (!(isConnected)) {
            networkChecker.checkConnectivity(this)
        }
    }

    private fun savePreferences() {
        val preferences = getSharedPreferences(PREFS_NAME, 0)
        val editor = preferences.edit()
        editor.putString(PREF_USERNAME, username.text.toString())
        editor.putString(PREF_PASSWORD, password.text.toString())
        editor.putBoolean(PREF_REMEMBER, true)
        editor.apply()
    }

    private fun clearPreferences() {
        val preferences = getSharedPreferences(PREFS_NAME, 0)
        val editor = preferences.edit()
        editor.remove(PREF_USERNAME)
        editor.remove(PREF_PASSWORD)
        editor.putBoolean(PREF_REMEMBER, false)
        editor.apply()
    }

    private fun loadPreferences() {
        val preferences = getSharedPreferences(PREFS_NAME, 0)
        val usernameValue = preferences.getString(PREF_USERNAME, "")
        val passwordValue = preferences.getString(PREF_PASSWORD, "")
        val rememberValue = preferences.getBoolean(PREF_REMEMBER, false)

        username.setText(usernameValue)
        password.setText(passwordValue)
        remember.isChecked = rememberValue
    }

    private fun checkBiometricPreference() {
        Log.d("LoginActivity", "Checking biometric preference")
        val preferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isBiometricEnabled = preferences.getBoolean("biometric_enabled", false)

        Log.d("LoginActivity", "Biometric enabled: $isBiometricEnabled")

        if (isBiometricEnabled) {
            biometricAuthentication = HandleBiometricAuthentication(this)
            biometricAuthentication.showBiometricPrompt()
        }
    }

    override fun onPingSuccess(responseCode: Int) {
        Log.d("Success", responseCode.toString())
    }

    override fun onPingFailure(exception: Exception) {
        Log.d("Error", exception.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        backendPinger.stop()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBiometricAuthSuccess() {
        Log.d("LoginActivity", "Biometric authentication succeeded!")
        val biometricUtils = BiometricAuthenticationUtils()
        val publicKey = biometricUtils.getPublicKey()

        if (publicKey != null) {
            settingsHandler.validateBiometricAuth(publicKey, object : HandleBiometricAuthentication.BiometricAuthCallback {
                override fun onBiometricAuthSuccess() {
                    Log.d("LoginActivity", "Biometric authentication succeeded!")
                    settingsHandler.getUsernameByKey(publicKey, object : HandleBiometricAuthentication.UsernameCallback {
                        override fun onUsernameReceived(username: String) {
                            Log.d("LoginActivity", "Checking username")
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("USERNAME", username)
                            startActivity(intent)
                        }
                    })

                }

                override fun onBiometricAuthError(errorCode: Int, errString: CharSequence) {
                    Log.d("LoginActivity", "Biometric authentication failed: $errString")
                }
            })
        } else {
            Log.d("LoginActivity", "No public key found in keystore")
        }
    }

    override fun onBiometricAuthError(errorCode: Int, errString: CharSequence) {
        Log.d("LoginActivity", "Biometric authentication error: $errString")
    }
}