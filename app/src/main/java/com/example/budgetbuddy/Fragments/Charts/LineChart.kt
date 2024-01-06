package com.example.budgetbuddy.Fragments.Charts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.budgetbuddy.DataClasses.UserData.UserExpenseDataResponse
import com.example.budgetbuddy.DataClasses.UserData.UserIncomeDataResponse
import com.example.budgetbuddy.Handlers.FiancesHandler.HandleExpenseCRUD
import com.example.budgetbuddy.Handlers.FiancesHandler.HandleIncomeCRUD
import com.example.budgetbuddy.R

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


private const val ARG_PARAM1 = "param1"

class LineChart : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1).toString()
        }
    }

    private lateinit var username: String

    private lateinit var incomeLineChart: LineChart
    private lateinit var expenseLineChart: LineChart

    private lateinit var incomeLineChartFetcher: HandleIncomeCRUD
    private lateinit var expenseLineChartFetcher: HandleExpenseCRUD

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_line_chart, container, false)

        incomeLineChart = view.findViewById(R.id.incomeLineChart)
        expenseLineChart = view.findViewById(R.id.expenseLineChart)

        incomeLineChartFetcher = HandleIncomeCRUD(requireContext())
        expenseLineChartFetcher = HandleExpenseCRUD(requireContext())

        incomeLineChartFetcher.fetchIncomeData(username, object : HandleIncomeCRUD.IncomeDataCallBack {
            override fun onIncomeDataReceived(incomeDataResponse: UserIncomeDataResponse) {
                updateIncomeChart(incomeDataResponse.income)
            }
        })

        expenseLineChartFetcher.fetchExpenseData(username, object : HandleExpenseCRUD.ExpenseDataCallBack {
            override fun onExpenseDataReceived(expenseDataResponse: UserExpenseDataResponse) {
                updateExpenseChart(expenseDataResponse.expense)
            }
        })

        return view
    }

    private fun updateIncomeChart(incomeData: List<List<Any>>) {
        val entries = mutableListOf<Entry>()

        incomeData.forEachIndexed { index, data ->
            if (data.size >= 2) {
                val label = data[0].toString()
                val value = data[1].toString().toFloat()
                entries.add(Entry(index.toFloat(), value, label))
            }
        }

        val dataSet = LineDataSet(entries, "Income Data")
        val lineData = LineData(dataSet)

        incomeLineChart.data = lineData
        incomeLineChart.invalidate()
    }

    private fun updateExpenseChart(expenseData: List<List<Any>>) {
        val entries = mutableListOf<Entry>()

        expenseData.forEachIndexed { index, data ->
            if (data.size >= 2) {
                val label = data[0].toString()
                val value = data[1].toString().toFloat()
                entries.add(Entry(index.toFloat(), value, label))
            }
        }

        val dataSet = LineDataSet(entries, "Expense Data")
        val lineData = LineData(dataSet)

        expenseLineChart.data = lineData
        expenseLineChart.invalidate()
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            LineChart().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                }
            }
    }

}
