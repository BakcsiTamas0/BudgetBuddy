package com.example.budgetbuddy.Fragments.Finances

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.Adapters.IncomeAdapter.CustomIncomeSpinnerAdapter
import com.example.budgetbuddy.Adapters.IncomeAdapter.IncomeRecyclerViewAdapter
import com.example.budgetbuddy.DataClasses.IncomeData.IncomeItem
import com.example.budgetbuddy.Handlers.FiancesHandler.HandleIncomeCRUD
import com.example.budgetbuddy.R
import android.content.Intent
import com.example.budgetbuddy.DataClasses.UserData.UserIncomeDataResponse

private const val ARG_PARAM1 = "username"
private const val ARG_PARAM2 = "userCurrency"

class IncomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1).toString()
            userCurrency = it.getString(ARG_PARAM2).toString()
        }
    }

    private lateinit var handleIncomeCRUD: HandleIncomeCRUD

    private lateinit var username: String
    private lateinit var userCurrency: String
    private lateinit var intent: Intent

    private lateinit var spinner: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var incomeList: MutableList<IncomeItem>
    private lateinit var addButton: androidx.appcompat.widget.AppCompatButton
    private lateinit var selectedCUR: TextView
    private lateinit var recyclerViewAdapter: IncomeRecyclerViewAdapter

    private lateinit var amountEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_income, container, false)

        handleIncomeCRUD = HandleIncomeCRUD(requireContext())
        handleIncomeCRUD.fetchIncomeData(username, object : HandleIncomeCRUD.IncomeDataCallBack {
            override fun onIncomeDataReceived(incomeDataResponse: UserIncomeDataResponse) {
                updateIncomeList(incomeDataResponse.income)
            }
        })

        Log.d("USERNAME", username)
        spinner = view.findViewById(R.id.incomeSpinner)

        val items = listOf(
            IncomeItem("Salary", R.drawable.salary),
            IncomeItem("Crypto", R.drawable.crypto),
            IncomeItem("Rental", R.drawable.rent),
            IncomeItem("Profit", R.drawable.profit),
            IncomeItem("Other", R.drawable.other)
        )

        val adapter = CustomIncomeSpinnerAdapter(requireContext(), items)
        spinner.adapter = adapter

        incomeList = mutableListOf()

        recyclerViewAdapter = IncomeRecyclerViewAdapter(requireContext(), incomeList, username)
        intent = Intent(requireContext(), IncomeFragment::class.java)
        intent.putExtra("USERNAME", username)

        recyclerView = view.findViewById(R.id.incomeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerViewAdapter

        handleIncomeCRUD = HandleIncomeCRUD(requireContext())

        addButton = view.findViewById(R.id.addIncome)
        amountEditText = view.findViewById(R.id.incomeAmount)
        selectedCUR = view.findViewById(R.id.selectedCUR)

        selectedCUR.text = userCurrency

        addButton.setOnClickListener() {
            val selectedIncome = spinner.selectedItem as IncomeItem
            val amount = amountEditText.text.toString()

            if (amount.isNotEmpty()) {
                val income = IncomeItem(selectedIncome.text, selectedIncome.imageId, amount.toDouble())

                if (username != null) {
                    handleIncomeCRUD.saveIncomeData(username, selectedIncome.text, amount.toDouble())
                }

                incomeList.add(income)
                recyclerViewAdapter.notifyItemInserted(incomeList.size - 1)
                updateTotalIncome()
                amountEditText.text.clear()
            } else {
                val shakeAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake_animation)
                amountEditText.startAnimation(shakeAnimation)

                val errorColor = ContextCompat.getColor(requireContext(), R.color.red)
                val originalBackground = amountEditText.background

                Handler(Looper.getMainLooper()).postDelayed({
                    amountEditText.background = originalBackground
                }, 2000)

                Toast.makeText(requireContext(), "Please enter an amount!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun updateIncomeList(incomeData: List<List<Any>>) {
        incomeList.clear()
        incomeData.forEach { item ->
            val income = IncomeItem(item[0].toString(), getDrawableIdForIncomeType(item[0].toString()), item[1].toString().toDouble())
            incomeList.add(income)
        }
        recyclerViewAdapter.notifyDataSetChanged()
        updateTotalIncome()
    }

    private fun getDrawableIdForIncomeType(incomeType: String): Int {
        return when (incomeType) {
            "Salary" -> R.drawable.salary
            "Crypto" -> R.drawable.crypto
            "Rental" -> R.drawable.rent
            "Profit" -> R.drawable.profit
            "Other" -> R.drawable.other
            else -> {R.drawable.other}
        }
    }

    private fun updateTotalIncome() {
        var totalIncome = 0.0
        incomeList.forEach { item ->
            totalIncome += item.amount!!.toDouble()
        }
        val totalIncomeView = view?.findViewById<TextView>(R.id.totalIncome)
        totalIncomeView?.text = String.format("%.2f", totalIncome)
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String, userCurrency: String) =
            IncomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                    putString(ARG_PARAM2, userCurrency)
                }
            }
    }
}