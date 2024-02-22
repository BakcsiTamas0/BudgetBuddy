package com.example.budgetbuddy.Fragments.RegionSettings

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.budgetbuddy.API.ExchangeAPI.ExchangeAPI
import com.example.budgetbuddy.Adapters.UserRegionSettingsAdapter.UserRegionSettingsCountrySpinnerAdapter
import com.example.budgetbuddy.Adapters.UserRegionSettingsAdapter.UserRegionSettingsCurrencySpinnerAdapter
import com.example.budgetbuddy.DataClasses.ExchangeData.ExchangeItem
import com.example.budgetbuddy.Handlers.RegionSettingsHandler.HandleRegionCRUD
import com.example.budgetbuddy.R
import com.example.budgetbuddy.Utils.RegionSettingsUtils
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RegionSettingsFragment : Fragment() {

    interface RegionSettingsListener {
        fun onSaveUserRegionSettings()
    }

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1).toString()
        }
    }

    private var regionSettingsListener: RegionSettingsListener? = null
    private lateinit var username: String

    private lateinit var userRegionSettingCountrySpinner: Spinner
    private lateinit var userRegionSettingCurrencySpinner: Spinner
    private lateinit var userRegionSettingSubmitButton: Button

    private lateinit var selectedCurrencies: List<String>

    private var country: String? = null
    private var currency: String? = null

    private val regionCRUD = HandleRegionCRUD()
    private val regionUtils = RegionSettingsUtils()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_region_settings, container, false)

        userRegionSettingCountrySpinner = view.findViewById(R.id.userRegionSettingCountrySpinner)
        userRegionSettingCurrencySpinner = view.findViewById(R.id.userRegionSettingCurrencySpinner)
        userRegionSettingSubmitButton = view.findViewById(R.id.userRegionSubmitButton)

        val exchangeAPI = ExchangeAPI("feea76798c5086d49afc470d")
        var exchangeRates: MutableList<ExchangeItem>


        selectedCurrencies = listOf("RON", "EUR", "USD", "GBP", "CAD", "CHF", "CNY", "CZK", "DKK", "HRK", "HUF", "JPY", "NOK", "PLN", "RUB", "SEK", "TRY")

        lifecycleScope.launch {
            exchangeRates = exchangeAPI.getExchangeRates("EUR").toMutableList()
            exchangeRates = exchangeRates.filter { it.currency in selectedCurrencies }.toMutableList()

            val countryAdapter = UserRegionSettingsCountrySpinnerAdapter(requireContext(), exchangeRates)
            val currencyAdapter = UserRegionSettingsCurrencySpinnerAdapter(requireContext(), exchangeRates)

            userRegionSettingCountrySpinner.adapter = countryAdapter
            userRegionSettingCurrencySpinner.adapter = currencyAdapter
        }

        userRegionSettingSubmitButton.setOnClickListener {
            val selectedCountryItem = userRegionSettingCountrySpinner.selectedItem as ExchangeItem
            val selectedCurrencyItem = userRegionSettingCurrencySpinner.selectedItem as ExchangeItem

            country = regionUtils.setFullCountryName(selectedCountryItem)
            currency = selectedCurrencyItem.currency

            regionCRUD.setRegion(username, country!!, currency!!)
            regionSettingsListener?.onSaveUserRegionSettings()

            parentFragmentManager.popBackStack()
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is RegionSettingsListener) {
            regionSettingsListener = context
        } else {
            throw RuntimeException("$context must implement RegionSettingsListener")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            RegionSettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                }
            }

    }
}