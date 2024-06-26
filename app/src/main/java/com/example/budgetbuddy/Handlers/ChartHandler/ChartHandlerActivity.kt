package com.example.budgetbuddy.Handlers.ChartHandler

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.budgetbuddy.Fragments.Charts.LineChart
import com.example.budgetbuddy.Fragments.Charts.PieChart
import com.example.budgetbuddy.Fragments.Charts.RadarChart
import com.example.budgetbuddy.R
import com.example.budgetbuddy.Utils.ConnectivityReceiver
import com.example.budgetbuddy.Utils.NetworkChecker
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ChartHandlerActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityChangeListener {
    private val connectivityReceiver = ConnectivityReceiver(this)
    private val networkChecker = NetworkChecker()
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart_handler)

        val username = intent.getStringExtra("USERNAME").toString()

        val fragmentList = listOf(
            LineChart.newInstance(username),
            PieChart.newInstance(username),
            RadarChart.newInstance(username)
        )

        tabLayout = findViewById(R.id.chartTabLayout)
        viewPager = findViewById(R.id.chartViewPager)

        val adapter = ChartPagerAdapter(this, fragmentList)

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Line"
                1 -> "Pie"
                2 -> "Radar"
                else -> null
            }
        }.attach()
    }

    private class ChartPagerAdapter(
        activity: AppCompatActivity,
        private val fragmentList: List<Fragment>
    ) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
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
}