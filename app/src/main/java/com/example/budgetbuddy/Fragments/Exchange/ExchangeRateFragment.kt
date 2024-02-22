package com.example.budgetbuddy.Fragments.Exchange

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.API.ExchangeAPI.ExchangeAPI
import com.example.budgetbuddy.API.RegionAPI.RegionAPI
import com.example.budgetbuddy.Adapters.ExchangeAdapter.ExchangeRecyclerViewAdapter
import com.example.budgetbuddy.DataClasses.ExchangeData.ExchangeItem
import com.example.budgetbuddy.DataClasses.RegionData.RegionResponse
import com.example.budgetbuddy.Handlers.RegionSettingsHandler.HandleRegionCRUD
import com.example.budgetbuddy.R
import com.example.budgetbuddy.Utils.RegionSettingsUtils
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ExchangeRateFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1).toString()
        }
    }

    private lateinit var username: String

    private val regionCRUD = HandleRegionCRUD()
    private val regionUtils = RegionSettingsUtils()

    private lateinit var exchangeRecyclerView: RecyclerView
    private lateinit var selectedCurrencies: List<String>

    private lateinit var defaultCountry: TextView
    private lateinit var defaultCurrency: TextView
    private lateinit var defaultCountryImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exchange_rate, container, false)

        defaultCountry = view.findViewById(R.id.defaultCountry)
        defaultCurrency = view.findViewById(R.id.defaultCurrency)
        defaultCountryImage = view.findViewById(R.id.defaultCountryImage)

        val exchangeAPI = ExchangeAPI("feea76798c5086d49afc470d")
        var exchangeRates: MutableList<ExchangeItem>

        selectedCurrencies = listOf("RON", "EUR", "USD", "GBP", "CAD", "CHF", "CNY", "CZK", "DKK", "HRK", "HUF", "JPY", "NOK", "PLN", "RUB", "SEK", "TRY")

        lifecycleScope.launch {
            exchangeRates = exchangeAPI.getExchangeRates("RON").toMutableList()
            exchangeRates = exchangeRates.filter { it.currency in selectedCurrencies }.toMutableList()

            exchangeRecyclerView = view.findViewById(R.id.exchangeRecyclerView)
            exchangeRecyclerView.layoutManager = LinearLayoutManager(requireContext())

            val adapter = ExchangeRecyclerViewAdapter(requireContext(), exchangeRates)
            exchangeRecyclerView.adapter = adapter
        }


        regionCRUD.getRegion(username, object : HandleRegionCRUD.onRegionResponseReceived {
            override fun onRegionResponseReceived(regionResponse: RegionResponse) {
                val regionData = regionResponse.response
                updateDefaultCountryAndRegion(regionData)
            }
        })

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            ExchangeRateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                }
            }
    }

    private fun updateDefaultCountryAndRegion(regionData: List<List<String>>) {
        defaultCountry.text = regionData[0][0]
        defaultCurrency.text = regionData[0][1]
    }
}