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
import com.example.budgetbuddy.Adapters.ExpenseAdapter.CustomExpenseSpinnerAdapter
import com.example.budgetbuddy.Adapters.ExpenseAdapter.ExpenseRecyclerViewAdapter
import com.example.budgetbuddy.DataClasses.ExpenseData.ExpenseItem
import com.example.budgetbuddy.DataClasses.UserData.UserExpenseDataResponse
import com.example.budgetbuddy.Handlers.FiancesHandler.HandleExpenseCRUD
import com.example.budgetbuddy.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ExpenseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1).toString()
        }
    }

    private lateinit var handleExpenseCRUD: HandleExpenseCRUD

    private lateinit var username: String
    private lateinit var intent: Intent

    private lateinit var spinner: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var expenseList: MutableList<ExpenseItem>
    private lateinit var addButton: Button
    private lateinit var recyclerViewAdapter: ExpenseRecyclerViewAdapter

    private lateinit var amountEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_expense, container, false)

        handleExpenseCRUD = HandleExpenseCRUD(requireContext())
        handleExpenseCRUD.fetchExpenseData(username, object : HandleExpenseCRUD.ExpenseDataCallBack {
            override fun onExpenseDataReceived(expenseDataResponse: UserExpenseDataResponse) {
                updateExpenseList(expenseDataResponse.expense)
            }
        })

        Log.d("USERNAME", username)
        spinner = view.findViewById(R.id.expenseSpinner)

        val items = listOf(
            ExpenseItem("Utilities", R.drawable.utilities),
            ExpenseItem("Transport", R.drawable.transportation),
            ExpenseItem("Taxes", R.drawable.taxes),
            ExpenseItem("Insurance", R.drawable.insurance),
            ExpenseItem("Fuel", R.drawable.fuel),
            ExpenseItem("Clothes", R.drawable.clothes),
            ExpenseItem("Food", R.drawable.food),
            ExpenseItem("Shoes", R.drawable.shoes),
            ExpenseItem("Other", R.drawable.other),
        )

        val adapter = CustomExpenseSpinnerAdapter(requireContext(), items)
        spinner.adapter = adapter

        expenseList = mutableListOf()

        recyclerViewAdapter = ExpenseRecyclerViewAdapter(requireContext(), expenseList, username)
        intent = Intent(requireContext(), ExpenseFragment::class.java)
        intent.putExtra("USERNAME", username)

        recyclerView = view.findViewById(R.id.expenseRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerViewAdapter

        handleExpenseCRUD = HandleExpenseCRUD(requireContext())

        addButton = view.findViewById(R.id.addExpense)
        amountEditText = view.findViewById(R.id.expenseAmount)

        addButton.setOnClickListener() {
            val selectedExpense = spinner.selectedItem as ExpenseItem
            val amount = amountEditText.text.toString()

            if (amount.isNotEmpty()) {
                val expense = ExpenseItem(selectedExpense.text, selectedExpense.imageId, amount.toDouble())

                if (username != null) {
                    handleExpenseCRUD.saveExpenseData(username, selectedExpense.text, amount.toDouble())
                }

                expenseList.add(expense)
                recyclerViewAdapter.notifyItemInserted(expenseList.size - 1)
                updateTotalExpense()
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

    private fun updateExpenseList(expenseData: List<List<Any>>) {
        expenseList.clear()
        expenseData.forEach { item ->
            val expense = ExpenseItem(item[0].toString(), getDrawableIdForExpenseType(item[0].toString()), item[1].toString().toDouble())
            expenseList.add(expense)
        }
        recyclerViewAdapter.notifyDataSetChanged()
        updateTotalExpense()
    }

    private fun getDrawableIdForExpenseType(expenseType: String): Int {
        return when (expenseType) {
            "Utilities" -> R.drawable.utilities
            "Transport" -> R.drawable.transportation
            "Taxes" -> R.drawable.taxes
            "Insurance" -> R.drawable.insurance
            "Fuel" -> R.drawable.fuel
            "Clothes" -> R.drawable.clothes
            "Food" -> R.drawable.food
            "Shoes" -> R.drawable.shoes
            "Other" -> R.drawable.other
            else -> R.drawable.other
        }
    }

    private fun updateTotalExpense() {
        var totalExpense = 0.0
        expenseList.forEach { item ->
            totalExpense += item.amount!!.toDouble()
        }
        val totalExpenseView = view?.findViewById<TextView>(R.id.totalExpense)
        totalExpenseView?.text = String.format("%.2f", totalExpense)
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            ExpenseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                }
            }
    }
}