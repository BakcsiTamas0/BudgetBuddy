package com.example.budgetbuddy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.budgetbuddy.Handlers.ChartHandler.ChartHandlerActivity
import com.example.budgetbuddy.Handlers.FiancesHandler.FinancesHandlerActivity
import com.example.budgetbuddy.Handlers.UserHandling.HandleUserDataFetching
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: androidx.drawerlayout.widget.DrawerLayout
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var handleUserDataFetching: HandleUserDataFetching
    private lateinit var drawerUsername: String

    private lateinit var navView: NavigationView

    private lateinit var financesTextView: TextView
    private lateinit var chartTextView: TextView
    private lateinit var settingsTextView: TextView

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
    }
}
