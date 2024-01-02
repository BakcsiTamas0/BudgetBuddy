package com.example.budgetbuddy.Fragments.Finances

import android.content.Intent
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
import com.example.budgetbuddy.Adapters.DebtAdapter.CustomDebtSpinnerAdapter
import com.example.budgetbuddy.Adapters.DebtAdapter.DebtRecyclerViewAdapter
import com.example.budgetbuddy.DataClasses.DebtData.DebtItem
import com.example.budgetbuddy.DataClasses.UserData.UserDebtDataResponse
import com.example.budgetbuddy.Handlers.FiancesHandler.HandleDebtCRUD
import com.example.budgetbuddy.R

private const val ARG_PARAM1 = "param1"

class DebtFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1).toString()
        }
    }

    private lateinit var username: String
    private lateinit var intent: Intent

    private lateinit var spinner: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var debtList: MutableList<DebtItem>
    private lateinit var addButton: Button
    private lateinit var recyclerViewAdapter: DebtRecyclerViewAdapter

    private lateinit var handleDebtCRUD: HandleDebtCRUD

    private lateinit var amountEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_debt, container, false)

        handleDebtCRUD = HandleDebtCRUD(requireContext())
        handleDebtCRUD.fetchDebtData(username, object : HandleDebtCRUD.DebtDataCallBack {
            override fun onDebtDataReceived(debtDataResponse: UserDebtDataResponse) {
                updateDebtList(debtDataResponse.debt)
            }
        })

        Log.d("USERNAME", username)
        spinner = view.findViewById(R.id.debtSpinner)

        val items = listOf(
            DebtItem("Loan", R.drawable.loan),
            DebtItem("Debt", R.drawable.debt),
            DebtItem("Other", R.drawable.other),
        )

        val adapter = CustomDebtSpinnerAdapter(requireContext(), items)
        spinner.adapter = adapter

        debtList = mutableListOf()

        recyclerViewAdapter = DebtRecyclerViewAdapter(requireContext(), debtList, username)
        intent = Intent(requireContext(), DebtFragment::class.java)
        intent.putExtra("USERNAME", username)

        recyclerView = view.findViewById(R.id.debtRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerViewAdapter

        handleDebtCRUD = HandleDebtCRUD(requireContext())

        addButton = view.findViewById(R.id.addDebt)
        amountEditText = view.findViewById(R.id.debtAmount)

        addButton.setOnClickListener() {
            val selectedDebt = spinner.selectedItem as DebtItem
            val amount = amountEditText.text.toString()

            if (amount.isNotEmpty()) {
                val debt = DebtItem(selectedDebt.text, selectedDebt.imageId, amount.toDouble())

                if (username != null) {
                    handleDebtCRUD.saveDebtData(username, selectedDebt.text, amount.toDouble())
                }

                debtList.add(debt)
                recyclerViewAdapter.notifyItemInserted(debtList.size - 1)
                updateTotalDebt()
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

    private fun updateDebtList(debtData: List<List<Any>>) {
        debtList.clear()
        debtData.forEach { item ->
            val debt = DebtItem(item[0].toString(), getDrawableIdForDebtType(item[0].toString()), item[1].toString().toDouble())
            debtList.add(debt)
        }
        recyclerViewAdapter.notifyDataSetChanged()
        updateTotalDebt()
    }

    private fun getDrawableIdForDebtType(debtType: String): Int {
        return when (debtType) {
            "Loan" -> R.drawable.loan
            "Debt" -> R.drawable.debt
            "Other" -> R.drawable.other
            else -> R.drawable.other
        }
    }

    private fun updateTotalDebt() {
        var totalDebt = 0.0
        debtList.forEach { item ->
            totalDebt += item.amount!!.toDouble()
        }
        val totalDebtView = view?.findViewById<TextView>(R.id.totalDebt)
        totalDebtView?.text = String.format("%.2f", totalDebt)
    }
    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            DebtFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                }
            }
    }
}