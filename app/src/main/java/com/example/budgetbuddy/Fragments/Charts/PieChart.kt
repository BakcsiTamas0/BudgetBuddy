package com.example.budgetbuddy.Fragments.Charts

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.budgetbuddy.DataClasses.ChartData.ExpenseData
import com.example.budgetbuddy.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

import com.example.budgetbuddy.Handlers.ChartHandler.ExpenseChartFetcher

private const val ARG_PARAM1 = "param1"

class PieChart : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1).toString()
        }
    }

    private lateinit var username: String
    private lateinit var expensePieChart: PieChart

    private val expensePRChartFetcher: ExpenseChartFetcher by lazy {
        ExpenseChartFetcher(requireContext(), username)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_pie_chart, container, false)

        expensePieChart = view.findViewById(R.id.expensePieChart)

        expensePRChartFetcher.fetchExpenseChartData { expenseDataList ->
            updatePieChart(expenseDataList)
        }
        return view
    }

    private fun updatePieChart(expenseDataList: List<ExpenseData>) {
        val entries = ArrayList<PieEntry>()

        for (expenseData in expenseDataList) {
            entries.add(PieEntry(expenseData.amount.toFloat(), expenseData.expenseType))
        }

        val dataSet = PieDataSet(entries, "")
        val colors = mutableListOf<Int>()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        dataSet.colors = colors

        val data = PieData(dataSet)

        expensePieChart.apply {
            setUsePercentValues(false)
            description.isEnabled = false
            legend.isEnabled = true
            holeRadius = 35f
            transparentCircleRadius = 45f

            data.setValueTextSize(14f)
            data.setValueTypeface(Typeface.DEFAULT_BOLD)
            data.setValueTextColor(ContextCompat.getColor(requireContext(), R.color.black))

            expensePieChart.data = data
            expensePieChart.invalidate()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            PieChart().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                }
            }
    }
}
