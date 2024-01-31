package com.example.budgetbuddy.Fragments.Exchange

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.API.ExchangeAPI.ExchangeAPI
import com.example.budgetbuddy.Adapters.ExchangeAdapter.ExchangeRecyclerViewAdapter
import com.example.budgetbuddy.DataClasses.ExchangeData.ExchangeItem
import com.example.budgetbuddy.R
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ExchangeRateFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var exchangeRecyclerView: RecyclerView
    private lateinit var selectedCurrencies: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exchange_rate, container, false)

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

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExchangeRateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}