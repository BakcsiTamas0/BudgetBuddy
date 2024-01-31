package com.example.budgetbuddy.Fragments.Exchange

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.budgetbuddy.API.ExchangeAPI.ExchangeAPI
import com.example.budgetbuddy.Adapters.ExchangeAdapter.ExchangeSpinnerAdapter
import com.example.budgetbuddy.DataClasses.ExchangeData.ExchangeItem
import com.example.budgetbuddy.R
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ExchangeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var exchangeOneSpinner: Spinner
    private lateinit var exchangeTwoSpinner: Spinner
    private lateinit var switchButton: ImageView

    private lateinit var selectedCurrencies: List<String>

    private lateinit var currencyOneTextView: TextView
    private lateinit var currencyTwoTextView: TextView

    private lateinit var buttonOne: Button
    private lateinit var buttonTwo: Button
    private lateinit var buttonThree: Button
    private lateinit var buttonFour: Button
    private lateinit var buttonFive: Button
    private lateinit var buttonSix: Button
    private lateinit var buttonSeven: Button
    private lateinit var buttonEight: Button
    private lateinit var buttonNine: Button
    private lateinit var buttonZero: Button
    private lateinit var buttonDot: Button
    private lateinit var buttonDelete: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exchange, container, false)

        exchangeOneSpinner = view.findViewById(R.id.exchangeOneSpinner)
        exchangeTwoSpinner = view.findViewById(R.id.exchangeTwoSpinner)
        switchButton = view.findViewById(R.id.switchButton)

        currencyOneTextView = view.findViewById(R.id.exchangeOneTextView)
        currencyTwoTextView = view.findViewById(R.id.exchangeTwoTextView)

        val numericButtons = listOf<Button>(
            view.findViewById(R.id.buttonOne),
            view.findViewById(R.id.buttonTwo),
            view.findViewById(R.id.buttonThree),
            view.findViewById(R.id.buttonFour),
            view.findViewById(R.id.buttonFive),
            view.findViewById(R.id.buttonSix),
            view.findViewById(R.id.buttonSeven),
            view.findViewById(R.id.buttonEight),
            view.findViewById(R.id.buttonNine),
            view.findViewById(R.id.buttonZero),
            view.findViewById(R.id.buttonDot),
            view.findViewById(R.id.buttonDel)
        )

        numericButtons.forEach { button ->
            button.setOnClickListener {
                updateCurrencyOne(button.text.toString())
                updateExchangeResult()
            }
        }

        val exchangeAPI = ExchangeAPI("feea76798c5086d49afc470d")
        var exchangeRates: MutableList<ExchangeItem>

        selectedCurrencies = listOf("RON", "EUR", "USD", "GBP", "CAD", "CHF", "CNY", "CZK", "DKK", "HRK", "HUF", "JPY", "NOK", "PLN", "RUB", "SEK", "TRY")

        lifecycleScope.launch {
            exchangeRates = exchangeAPI.getExchangeRates("RON").toMutableList()
            exchangeRates = exchangeRates.filter { it.currency in selectedCurrencies }.toMutableList()

            val adapter = ExchangeSpinnerAdapter(requireContext(), exchangeRates)
            exchangeOneSpinner.adapter = adapter
            exchangeTwoSpinner.adapter = adapter
        }

        return view
    }

    private fun updateCurrencyOne(input: String) {
        val currentAmount = currencyOneTextView.text.toString()
        if (input == "Del") {
            if (currentAmount.isNotEmpty()) {
                currencyOneTextView.text = currentAmount.substring(0, currentAmount.length - 1)
            }
        } else {
            currencyOneTextView.text = currentAmount + input
        }
    }

    private fun updateExchangeResult() {
        val amountString = currencyOneTextView.text.toString()
        if (amountString.isNotBlank()) {
            val amount = amountString.toDouble()

            val selectedCurrencyOne = exchangeOneSpinner.selectedItem as ExchangeItem
            val selectedCurrencyTwo = exchangeTwoSpinner.selectedItem as ExchangeItem

            val result = calculateExchange(amount, selectedCurrencyOne.rate, selectedCurrencyTwo.rate)
            currencyTwoTextView.text = result.toString()
        }
    }

    private fun calculateExchange(amount: Double, rateCurrencyOne: Double, rateCurrencyTwo: Double): Double {
        return (amount / rateCurrencyOne) * rateCurrencyTwo
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExchangeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}