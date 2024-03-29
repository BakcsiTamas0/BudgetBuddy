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
import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import com.example.budgetbuddy.Fragments.RegionSettings.RegionSettingsFragment
import com.example.budgetbuddy.Handlers.ChatBotMessageHandler.HandleChatBotMessages
import com.example.budgetbuddy.Handlers.ExchangeHandler.ExchangeHandlerActivity
import com.example.budgetbuddy.Handlers.StatisticsGenerationHandler.HandleStatisticsGeneration
import com.example.budgetbuddy.R.id.drawerEmail
import com.example.budgetbuddy.Utils.ConnectivityReceiver
import com.example.budgetbuddy.Utils.NetworkChecker

class MainActivity : AppCompatActivity(), RegionSettingsFragment.RegionSettingsListener, ConnectivityReceiver.ConnectivityChangeListener {
    private val connectivityReceiver = ConnectivityReceiver(this)
    private val networkChecker = NetworkChecker()

    private var isExpanded = false
    private var initialCollapsedHeight = 0

    private lateinit var drawerLayout: androidx.drawerlayout.widget.DrawerLayout
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var handleUserDataFetching: HandleUserDataFetching

    private lateinit var drawerUsername: String
    private lateinit var navView: NavigationView

    private lateinit var profileTextView: TextView
    private lateinit var financesTextView: TextView
    private lateinit var graphsTextView: TextView
    private lateinit var generatedStatisticsTextView: TextView
    private lateinit var settingsTextView: TextView

    private lateinit var appListFinances: LinearLayout
    private lateinit var appListGraphs: LinearLayout
    private lateinit var appListStatistics: LinearLayout
    private lateinit var appListExchange: LinearLayout

    private lateinit var expandButton: Button
    private lateinit var chatSendButton: Button
    private lateinit var userMessageInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUIElements()
        initLogic()


        if (!hasUserRegionSettings()) {
            showUserRegionSettingsFragment()
        }

    }

    override fun onResume() {
        super.onResume()
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

    override fun onSaveUserRegionSettings() {
        saveUserRegionSettings()
    }

    private fun initUIElements(): Boolean {
        drawerLayout = findViewById(R.id.drawerLayout)
        toolbar = findViewById(R.id.toolbar)

        navView = findViewById(R.id.nav_view)

        profileTextView = findViewById(R.id.profileTextView)
        financesTextView = findViewById(R.id.financesTextView)
        graphsTextView = findViewById(R.id.chartTextView)
        generatedStatisticsTextView = findViewById(R.id.generatedStatisticsTextView)
        settingsTextView = findViewById(R.id.settingsTextView)

        appListFinances = findViewById(R.id.appListFinances)
        appListGraphs = findViewById(R.id.appListGraphs)
        appListStatistics = findViewById(R.id.appListStatistics)
        appListExchange = findViewById(R.id.appListExchange)

        expandButton = findViewById(R.id.expandButton)

        chatSendButton = findViewById(R.id.sendUserMessageInput)
        userMessageInput = findViewById(R.id.userMessageInput)

        return true
    }

    @SuppressLint("StringFormatInvalid")
    private fun initLogic() {
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.title = ""
        toolbar.subtitle = ""

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        drawerUsername = intent.getStringExtra("USERNAME").toString()

        handleUserDataFetching = HandleUserDataFetching(this, findViewById(R.id.drawerUsername), findViewById(drawerEmail))
        handleUserDataFetching.fetchData(drawerUsername)

        val statisticsHandler = HandleStatisticsGeneration(this)
        statisticsHandler.getWeeklyEstimatedSpending(drawerUsername, object: HandleStatisticsGeneration.EstimatedExpenseCallback {
            override fun onEstimatedExpenseCallback(response: String) {
                val weeklyEstimatedExpense = response
            }
        })


        profileTextView.setOnClickListener {
            val profileIntent = Intent(this, ProfileActivity::class.java)
            profileIntent.putExtra("USERNAME", drawerUsername)
            profileIntent.putExtra("EMAIL", drawerEmail.toString())
            startActivity(profileIntent)
            drawerLayout.closeDrawers()
        }

        financesTextView.setOnClickListener {
            val financesIntent = Intent(this, FinancesHandlerActivity::class.java)
            financesIntent.putExtra("USERNAME", drawerUsername)
            startActivity(financesIntent)
            drawerLayout.closeDrawers()
        }

        graphsTextView.setOnClickListener {
            val chartIntent = Intent(this, ChartHandlerActivity::class.java)
            chartIntent.putExtra("USERNAME", drawerUsername)
            startActivity(chartIntent)
            drawerLayout.closeDrawers()
        }

        generatedStatisticsTextView.setOnClickListener {
            val generatedStatisticsIntent = Intent(this, GeneralStatisticsActivity::class.java)
            generatedStatisticsIntent.putExtra("USERNAME", drawerUsername)
            startActivity(generatedStatisticsIntent)
        }

        settingsTextView.setOnClickListener {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            settingsIntent.putExtra("USERNAME", drawerUsername)
            startActivity(settingsIntent)
        }

        appListFinances.setOnClickListener {
            val financesIntent = Intent(this, FinancesHandlerActivity::class.java)
            financesIntent.putExtra("USERNAME", drawerUsername)
            startActivity(financesIntent)
        }

        appListGraphs.setOnClickListener {
            val chartIntent = Intent(this, ChartHandlerActivity::class.java)
            chartIntent.putExtra("USERNAME", drawerUsername)
            startActivity(chartIntent)
        }

        appListStatistics.setOnClickListener {
            val generatedStatisticsIntent = Intent(this, GeneralStatisticsActivity::class.java)
            generatedStatisticsIntent.putExtra("USERNAME", drawerUsername)
            startActivity(generatedStatisticsIntent)
        }

        appListExchange.setOnClickListener {
            val exchangeIntent = Intent(this, ExchangeHandlerActivity::class.java)
            exchangeIntent.putExtra("USERNAME", drawerUsername)
            startActivity(exchangeIntent)
        }

        expandButton.setOnClickListener {
            toggleExpandCollapse()
        }

        chatSendButton.setOnClickListener {
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

    private fun showUserRegionSettingsFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val regionSettingsFragment = RegionSettingsFragment.newInstance(drawerUsername)
        fragmentTransaction.replace(android.R.id.content, regionSettingsFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
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

        val chatBotMessageLayout = LayoutInflater.from(this)
            .inflate(R.layout.custom_chatbot_message_frame, null, false)

        val chatBotMessageTextView = chatBotMessageLayout.findViewById<TextView>(R.id.chatbot_message_text_view)
        chatBotMessageTextView.text = response
        chatBotMessageTextView.movementMethod = ScrollingMovementMethod()

        chatLinearLayout.addView(chatBotMessageTextView)

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
