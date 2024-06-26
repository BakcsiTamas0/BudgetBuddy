package com.example.budgetbuddy.Fragments.Charts

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.budgetbuddy.DataClasses.ChartData.ExpenseData
import com.example.budgetbuddy.DataClasses.ChartData.IncomeData
import com.example.budgetbuddy.Handlers.ChartHandler.ExpenseChartFetcher
import com.example.budgetbuddy.Handlers.ChartHandler.IncomePRChartFetcher
import com.example.budgetbuddy.R
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry

private const val ARG_PARAM1 = "param1"

class RadarChart : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1).toString()
        }
    }

    private lateinit var username: String

    private lateinit var incomeRadarChart: com.github.mikephil.charting.charts.RadarChart
    private lateinit var expenseRadarChart: com.github.mikephil.charting.charts.RadarChart

    private val incomePRChartFetcher: IncomePRChartFetcher by lazy {
        IncomePRChartFetcher(requireContext(), username)
    }

    private val expensePRChartFetcher: ExpenseChartFetcher by lazy {
        ExpenseChartFetcher(requireContext(), username)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_radar_chart, container, false)

        incomeRadarChart = view.findViewById(R.id.incomeRadarChart)
        expenseRadarChart = view.findViewById(R.id.expenseRadarChart)

        expensePRChartFetcher.fetchExpenseChartData { expenseDataList ->
            updateExpenseRadarChart(expenseDataList)
        }

        incomePRChartFetcher.fetchIncomeChartData { incomeDataList ->
            updateIncomeRadarChart(incomeDataList)
        }

        return view
    }

    private fun updateExpenseRadarChart(expenseDataList: List<ExpenseData>) {
        val entries = ArrayList<RadarEntry>()
        val labels = ArrayList<String>()

        for (expenseData in expenseDataList) {
            entries.add(RadarEntry(expenseData.amount.toFloat()))
            labels.add(expenseData.expenseType)
        }

        val dataSet = RadarDataSet(entries, "Expenses")

        dataSet.color = Color.rgb(103, 110, 129)
        dataSet.fillColor = Color.rgb(103, 110, 129)
        dataSet.setDrawFilled(true)
        dataSet.fillAlpha = 180
        dataSet.lineWidth = 2f

        val data = RadarData(dataSet)
        data.setValueTextSize(14f)
        data.setValueTextColor(Color.BLACK)

        expenseRadarChart.apply {
            description.isEnabled = false
            webLineWidth = 1f
            webColor = Color.LTGRAY
            webLineWidthInner = 1f
            webColorInner = Color.LTGRAY
            webAlpha = 100

            xAxis.valueFormatter = com.github.mikephil.charting.formatter.IndexAxisValueFormatter(labels)
            xAxis.textSize = 12f

            this.data = data
            invalidate()
        }
    }

    private fun updateIncomeRadarChart(incomeDataList: List<IncomeData>) {
        val entries = ArrayList<RadarEntry>()
        val labels = ArrayList<String>()

        for (incomeData in incomeDataList) {
            entries.add(RadarEntry(incomeData.amount.toFloat()))
            labels.add(incomeData.incomeType)
        }

        val dataSet = RadarDataSet(entries, "Income")

        dataSet.color = Color.rgb(103, 110, 129)
        dataSet.fillColor = Color.rgb(103, 110, 129)
        dataSet.setDrawFilled(true)
        dataSet.fillAlpha = 180
        dataSet.lineWidth = 2f

        val data = RadarData(dataSet)
        data.setValueTextSize(14f)
        data.setValueTextColor(Color.BLACK)

        incomeRadarChart.apply {
            description.isEnabled = false
            webLineWidth = 1f
            webColor = Color.LTGRAY
            webLineWidthInner = 1f
            webColorInner = Color.LTGRAY
            webAlpha = 100

            xAxis.valueFormatter = com.github.mikephil.charting.formatter.IndexAxisValueFormatter(labels)
            xAxis.textSize = 12f

            this.data = data
            invalidate()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            RadarChart().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                }
            }
    }
}
