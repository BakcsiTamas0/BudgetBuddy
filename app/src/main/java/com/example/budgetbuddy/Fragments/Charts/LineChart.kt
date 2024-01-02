package com.example.budgetbuddy.Fragments.Charts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.budgetbuddy.R

import com.github.mikephil.charting.charts.LineChart


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_line_chart, container, false)

        incomeLineChart = view.findViewById(R.id.incomeLineChart)
        expenseLineChart = view.findViewById(R.id.expenseLineChart)

        return view
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
