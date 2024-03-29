package com.example.budgetbuddy.Handlers.FiancesHandler

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.budgetbuddy.DataClasses.RegionData.RegionResponse
import com.example.budgetbuddy.Fragments.Finances.DebtFragment
import com.example.budgetbuddy.Fragments.Finances.ExpenseFragment
import com.example.budgetbuddy.Fragments.Finances.IncomeFragment
import com.example.budgetbuddy.Handlers.RegionSettingsHandler.HandleRegionCRUD
import com.example.budgetbuddy.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FinancesHandlerActivity : AppCompatActivity() {

    private val handleRegion = HandleRegionCRUD()

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    private lateinit var userCurrency: String

    private var response: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finances_handler)

        val username = intent.getStringExtra("USERNAME").toString()

        handleRegion.getRegion(username, object: HandleRegionCRUD.onRegionResponseReceived {
            override fun onRegionResponseReceived(regionResponse: RegionResponse) {
                response = regionResponse.response[0][1]

                userCurrency = response.toString()

                val fragmentList = listOf(
                    IncomeFragment.newInstance(username, userCurrency),
                    ExpenseFragment.newInstance(username, userCurrency),
                    DebtFragment.newInstance(username, userCurrency)
                )

                tabLayout = findViewById(R.id.tabLayout)
                viewPager = findViewById(R.id.viewPager)

                val adapter = FinancesPagerAdapter(this@FinancesHandlerActivity, fragmentList, username, userCurrency)

                viewPager.adapter = adapter

                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = when (position) {
                        0 -> "Income"
                        1 -> "Expense"
                        2 -> "Debt"
                        else -> null
                    }
                }.attach()
            }
        })

        userCurrency = response.toString()

        val fragmentList = listOf(
            IncomeFragment.newInstance(username, userCurrency),
            ExpenseFragment.newInstance(username, userCurrency),
            DebtFragment.newInstance(username, userCurrency)
        )

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        val adapter = FinancesPagerAdapter(this, fragmentList, username, userCurrency)

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Income"
                1 -> "Expense"
                2 -> "Debt"
                else -> null
            }
        }.attach()
    }

    private class FinancesPagerAdapter(
        activity: AppCompatActivity,
        private val fragmentList: List<Fragment>,
        private val username: String?,
        private val userCurrency: String
    ) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return if (position == 0) {
                IncomeFragment.newInstance(username ?: "", userCurrency)
            } else {
                fragmentList[position]
            }
        }
    }
}