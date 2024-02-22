package com.example.budgetbuddy.Handlers.ExchangeHandler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.budgetbuddy.Fragments.Exchange.ExchangeFragment
import com.example.budgetbuddy.Fragments.Exchange.ExchangeRateFragment
import com.example.budgetbuddy.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ExchangeHandlerActivity : AppCompatActivity() {

    private lateinit var exchangeTabLayout: TabLayout
    private lateinit var exchangeViewPager: ViewPager2

    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_handler)

        username = intent.getStringExtra("USERNAME").toString()

        val fragmentList = listOf(
            ExchangeFragment(),
            ExchangeRateFragment.newInstance(username)
        )

        exchangeTabLayout = findViewById(R.id.exchangeTabLayout)
        exchangeViewPager = findViewById(R.id.exchangeViewPager)

        val adapter = ExchangePagerAdapter(this, fragmentList)

        exchangeViewPager.adapter = adapter

        TabLayoutMediator(exchangeTabLayout, exchangeViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Exchange"
                1 -> "Exchange Rate"
                else -> null
            }
        }.attach()
    }

    private class ExchangePagerAdapter(
        activity: AppCompatActivity,
        private val fragmentList: List<Fragment>
    ) : FragmentStateAdapter (activity) {

        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }
}