package com.example.budgetbuddy

import android.content.Intent
import android.os.Bundle

import android.widget.Button
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.budgetbuddy.Handlers.ChartHandler.ChartHandlerActivity
import com.example.budgetbuddy.Handlers.FiancesHandler.FinancesHandlerActivity
import com.example.budgetbuddy.Handlers.UserHandling.HandleUserDataFetching
import com.example.budgetbuddy.R.id.appListCardOne
import com.google.android.material.navigation.NavigationView
import android.animation.ValueAnimator
import android.app.Dialog
import android.graphics.Bitmap
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import com.example.budgetbuddy.API.ExchangeAPI.ExchangeAPI
import com.example.budgetbuddy.Adapters.UserRegionSettingsAdapter.UserRegionSettingsCountrySpinnerAdapter
import com.example.budgetbuddy.Adapters.UserRegionSettingsAdapter.UserRegionSettingsCurrencySpinnerAdapter
import com.example.budgetbuddy.DataClasses.ExchangeData.ExchangeItem
import com.example.budgetbuddy.Handlers.ChatBotMessageHandler.HandleChatBotMessages
import com.example.budgetbuddy.Handlers.ExchangeHandler.ExchangeHandlerActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var isExpanded = false
    private var initialCollapsedHeight = 0

    private lateinit var userRegionSettingCurrencySpinner: Spinner

    private lateinit var drawerLayout: androidx.drawerlayout.widget.DrawerLayout
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var handleUserDataFetching: HandleUserDataFetching
    private lateinit var drawerUsername: String

    private lateinit var navView: NavigationView

    private lateinit var financesTextView: TextView
    private lateinit var chartTextView: TextView
    private lateinit var settingsTextView: TextView

    private lateinit var appListExchangeOptionTextView: TextView

    private lateinit var expandButton: Button
    private lateinit var chatSendButton: Button
    private lateinit var userMessageInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!hasUserRegionSettings()) {
            showUserRegionSettingsDialog()
        } else {
            initUIElements()
            initLogic()
        }

    }

    private fun initUIElements(): Boolean {
        userRegionSettingCurrencySpinner = findViewById(R.id.userRegionSettingCurrencySpinner)

        drawerLayout = findViewById(R.id.drawerLayout)
        toolbar = findViewById(R.id.toolbar)

        navView = findViewById(R.id.nav_view)

        financesTextView = findViewById(R.id.financesTextView)
        chartTextView = findViewById(R.id.chartTextView)
        settingsTextView = findViewById(R.id.settingsTextView)

        appListExchangeOptionTextView = findViewById(R.id.appListExchangeOptionTextView)

        expandButton = findViewById(R.id.expandButton)

        chatSendButton = findViewById(R.id.sendUserMessageInput)
        userMessageInput = findViewById(R.id.userMessageInput)

        return true
    }

    private fun initLogic() {
        setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        drawerUsername = intent.getStringExtra("USERNAME").toString()

        handleUserDataFetching = HandleUserDataFetching(this, findViewById(R.id.drawerUsername), findViewById(R.id.drawerEmail))
        handleUserDataFetching.fetchData(drawerUsername)

        financesTextView.setOnClickListener() {
            val financesIntent = Intent(this, FinancesHandlerActivity::class.java)
            financesIntent.putExtra("USERNAME", drawerUsername)
            startActivity(financesIntent)
            drawerLayout.closeDrawers()
        }

        chartTextView.setOnClickListener() {
            val chartIntent = Intent(this, ChartHandlerActivity::class.java)
            chartIntent.putExtra("USERNAME", drawerUsername)
            startActivity(chartIntent)
            drawerLayout.closeDrawers()
        }

        settingsTextView.setOnClickListener() {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }

        appListExchangeOptionTextView.setOnClickListener() {
            val exchangeIntent = Intent(this, ExchangeHandlerActivity::class.java)
            startActivity(exchangeIntent)
        }

        expandButton.setOnClickListener() {
            toggleExpandCollapse()
        }

        chatSendButton.setOnClickListener() {
            Toast.makeText(this, "Sending message...", Toast.LENGTH_SHORT).show()

            val userMessage = userMessageInput.text.toString()

            val handleChatBotMessages = HandleChatBotMessages(this)
            handleChatBotMessages.sendChatBotMessage(drawerUsername, userMessage) { response ->
                Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show()
                handleResponse(response)
                Toast.makeText(this, "Message handled!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hasUserRegionSettings(): Boolean {
        val sharedPref = getSharedPreferences("userRegionSettings", MODE_PRIVATE)
        return sharedPref.contains("hasUserSetRegion") && sharedPref.getBoolean("hasUserSetRegion", false)
    }

    private fun showUserRegionSettingsDialog() {
        val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.setContentView(R.layout.user_region_settings)

        val userRegionSettingCountrySpinner = dialog.findViewById<Spinner>(R.id.userRegionSettingCountrySpinner)
        val userRegionSettingCurrencySpinner = dialog.findViewById<Spinner>(R.id.userRegionSettingCurrencySpinner)

        val exchangeAPI = ExchangeAPI("feea76798c5086d49afc470d")
        var exchangeRates: MutableList<ExchangeItem>

        lifecycleScope.launch {
            exchangeRates = exchangeAPI.getExchangeRates("EUR").toMutableList()

            val countryAdapter = UserRegionSettingsCountrySpinnerAdapter(this@MainActivity, exchangeRates)
            val currencyAdapter = UserRegionSettingsCurrencySpinnerAdapter(this@MainActivity, exchangeRates)

            userRegionSettingCountrySpinner.adapter = countryAdapter
            userRegionSettingCurrencySpinner.adapter = currencyAdapter
        }

        val submitButton = dialog.findViewById<Button>(R.id.userRegionSubmitButton)
        submitButton.setOnClickListener {
            saveUserRegionSettings()
            dialog.dismiss()

            setContentView(R.layout.activity_main)
            initUIElements()
            initLogic()
        }

        dialog.show()
    }

    private fun saveUserRegionSettings() {
        val sharedPref = getSharedPreferences("userRegionSettings", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("hasUserSetRegion", true)
            apply()
        }
    }

    private fun handleResponse(response: String) {
        val chatLinearLayout = findViewById<LinearLayout>(R.id.chat_relative_layout)

        val userMessageLayout = LayoutInflater.from(this)
            .inflate(R.layout.custom_user_message_frame, null, false)

        val userMessageTextView = userMessageLayout.findViewById<TextView>(R.id.user_message_text_view)

        val userMessageUsername = userMessageLayout.findViewById<TextView>(R.id.chat_username)
        userMessageUsername.text = drawerUsername

        userMessageTextView.text = userMessageInput.text.toString()
        userMessageTextView.textSize = 16f

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.bottomMargin = 30
        userMessageLayout.layoutParams = layoutParams

        chatLinearLayout.addView(userMessageLayout)

        val chatbotMessageLayout = LayoutInflater.from(this)
            .inflate(R.layout.custom_chatbot_message_frame, null, false)

        val chatbotMessageTextView = chatbotMessageLayout.findViewById<TextView>(R.id.chatbot_message_text_view)
        chatbotMessageTextView.text = response
        chatbotMessageTextView.movementMethod = ScrollingMovementMethod()

        chatLinearLayout.addView(chatbotMessageLayout)

        val scrollView = findViewById<NestedScrollView>(R.id.scrollView)
        scrollView.post { scrollView.fullScroll(View.FOCUS_DOWN) }

        userMessageInput.text.clear()
    }

    private fun toggleExpandCollapse() {
        val statisticsAndChatLayout = findViewById<CardView>(appListCardOne)
        val layoutParams = statisticsAndChatLayout.layoutParams as FrameLayout.LayoutParams

        if (initialCollapsedHeight == 0) {
            initialCollapsedHeight = layoutParams.height
        }

        val animator = if (isExpanded) {
            ValueAnimator.ofInt(layoutParams.height, initialCollapsedHeight)
        } else {
            val screenHeight = resources.displayMetrics.heightPixels
            val expandedHeight = (screenHeight * 0.62).toInt()
            ValueAnimator.ofInt(layoutParams.height, expandedHeight)
        }

        animator.addUpdateListener { valueAnimator ->
            layoutParams.height = valueAnimator.animatedValue as Int
            statisticsAndChatLayout.layoutParams = layoutParams
        }

        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = 700

        animator.start()

        val expandButtonParams = expandButton.layoutParams as RelativeLayout.LayoutParams
        expandButtonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, if (isExpanded) 0 else 1)
        expandButton.layoutParams = expandButtonParams

        expandButton.text = if (isExpanded) "Expand" else "Collapse"

        isExpanded = !isExpanded
    }

}
