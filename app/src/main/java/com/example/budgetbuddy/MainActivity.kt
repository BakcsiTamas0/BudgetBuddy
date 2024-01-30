package com.example.budgetbuddy

import android.content.Intent
import android.os.Bundle
import android.util.Log

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
import android.view.animation.AccelerateDecelerateInterpolator
import com.example.budgetbuddy.Handlers.ExchangeHandler.ExchangeHandlerActivity

class MainActivity : AppCompatActivity() {

    private var isExpanded = false
    private var initialCollapsedHeight = 0

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        drawerUsername = intent.getStringExtra("USERNAME").toString()

        handleUserDataFetching = HandleUserDataFetching(this, findViewById(R.id.drawerUsername), findViewById(R.id.drawerEmail))
        handleUserDataFetching.fetchData(drawerUsername)

        navView = findViewById(R.id.nav_view)

        navView.setOnClickListener() {
            Log.d("TAG", "clicked")
        }

        financesTextView = findViewById(R.id.financesTextView)
        chartTextView = findViewById(R.id.chartTextView)
        settingsTextView = findViewById(R.id.settingsTextView)

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

        appListExchangeOptionTextView = findViewById(R.id.appListExchangeOptionTextView)

        appListExchangeOptionTextView.setOnClickListener() {
            val exchangeIntent = Intent(this, ExchangeHandlerActivity::class.java)
            startActivity(exchangeIntent)
        }

        expandButton = findViewById(R.id.expandButton)

        expandButton.setOnClickListener() {
            toggleExpandCollapse()
        }
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
