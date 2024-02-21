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
import com.example.budgetbuddy.R
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
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var regionSettingsListener: RegionSettingsListener? = null

    private lateinit var userRegionSettingCountrySpinner: Spinner
    private lateinit var userRegionSettingCurrencySpinner: Spinner
    private lateinit var userRegionSettingSubmitButton: Button

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

        lifecycleScope.launch {
            exchangeRates = exchangeAPI.getExchangeRates("EUR").toMutableList()

            val countryAdapter = UserRegionSettingsCountrySpinnerAdapter(requireContext(), exchangeRates)
            val currencyAdapter = UserRegionSettingsCurrencySpinnerAdapter(requireContext(), exchangeRates)

            userRegionSettingCountrySpinner.adapter = countryAdapter
            userRegionSettingCurrencySpinner.adapter = currencyAdapter
        }

        userRegionSettingSubmitButton.setOnClickListener {
            Log.d("RegionSettings", "Submit button clicked.")
            Log.d("RegionSettings", "Country: ${userRegionSettingCountrySpinner.selectedItem.toString()}")
            Log.d("RegionSettings", "Currency: ${userRegionSettingCurrencySpinner.selectedItem.toString()}")
            regionSettingsListener?.onSaveUserRegionSettings()
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
        fun newInstance() =
            RegionSettingsFragment()
    }
}