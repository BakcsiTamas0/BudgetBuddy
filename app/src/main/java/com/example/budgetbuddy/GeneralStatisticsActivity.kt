package com.example.budgetbuddy

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.budgetbuddy.DataClasses.RegionData.RegionResponse
import com.example.budgetbuddy.DataClasses.StatisticsData.StatisticsDataResponse
import com.example.budgetbuddy.DataClasses.UserData.UserDebtDataResponse
import com.example.budgetbuddy.DataClasses.UserData.UserExpenseDataResponse
import com.example.budgetbuddy.DataClasses.UserData.UserIncomeDataResponse
import com.example.budgetbuddy.Handlers.FiancesHandler.HandleDebtCRUD
import com.example.budgetbuddy.Handlers.FiancesHandler.HandleExpenseCRUD
import com.example.budgetbuddy.Handlers.FiancesHandler.HandleIncomeCRUD
import com.example.budgetbuddy.Handlers.RegionSettingsHandler.HandleRegionCRUD
import com.example.budgetbuddy.Handlers.StatisticsGenerationHandler.HandleStatisticsGeneration
import com.example.budgetbuddy.Utils.ConnectivityReceiver
import com.example.budgetbuddy.Utils.NetworkChecker

class GeneralStatisticsActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityChangeListener {
    private val connectivityReceiver = ConnectivityReceiver(this)
    private val networkChecker = NetworkChecker()
    private var handleRegion = HandleRegionCRUD()

    private lateinit var statisticsUsername: TextView
    private lateinit var statisticsIncome: TextView
    private lateinit var statisticsExpense: TextView
    private lateinit var statisticsDebt: TextView
    private lateinit var statisticsAverageWeeklySpending: TextView
    private lateinit var statisticsAverageMonthlySpending: TextView
    private lateinit var statisticsEstimatedSpending: TextView
    private lateinit var statisticsMaxWeeklySpending: TextView
    private lateinit var statisticsMaxMonthlySpending: TextView
    private lateinit var statisticsMaxWeeklySpendingItem: TextView
    private lateinit var statisticsMinWeeklySpendingItem: TextView
    private lateinit var statisticsMaxMonthlySpendingItem: TextView
    private lateinit var statisticsMinMonthlySpendingItem: TextView

    private lateinit var generateStatisticsButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_statistics)

        val username: String = intent.getStringExtra("USERNAME").toString()

        val incomeFetch  = HandleIncomeCRUD(this)
        val expenseFetch = HandleExpenseCRUD(this)
        val debtFetch    = HandleDebtCRUD(this)

        val statisticsHandler = HandleStatisticsGeneration(this)

        statisticsUsername = findViewById(R.id.statisticsUsername)
        statisticsIncome = findViewById(R.id.statisticsIncome)
        statisticsExpense = findViewById(R.id.statisticsExpense)
        statisticsDebt = findViewById(R.id.statisticsDebt)
        statisticsAverageWeeklySpending = findViewById(R.id.statisticsAverageWeeklySpending)
        statisticsAverageMonthlySpending = findViewById(R.id.statisticsAverageMonthlySpending)
        statisticsEstimatedSpending = findViewById(R.id.statisticsEstimatedSpending)

        statisticsMaxWeeklySpending =  findViewById(R.id.statisticsMaxWeeklySpending)
        statisticsMaxMonthlySpending = findViewById(R.id.statisticsMaxMonthlySpending)

        statisticsMaxWeeklySpendingItem = findViewById(R.id.statisticsMaxWeeklySpendingItem)
        statisticsMinWeeklySpendingItem = findViewById(R.id.statisticsMinWeeklySpendingItem)

        statisticsMaxMonthlySpendingItem = findViewById(R.id.statisticsMaxMonthlySpendingItem)
        statisticsMinMonthlySpendingItem = findViewById(R.id.statisticsMinMonthlySpendingItem)

        val statisticsGeneration = HandleStatisticsGeneration(this)
        statisticsGeneration.getExpenseStatistics(username, object: HandleStatisticsGeneration.StatisticsDataCallBack {
            override fun onStatisticsDataReceived(statisticsDataResponse: StatisticsDataResponse?) {
                Log.d("GeneralStatisticsActivity", statisticsDataResponse.toString())
                calcWeeklySpending(
                    statisticsDataResponse!!,
                    statisticsAverageWeeklySpending,
                    statisticsMaxWeeklySpending,
                    statisticsMaxWeeklySpendingItem,
                    statisticsMinWeeklySpendingItem)

                calcMonthlySpending(
                    statisticsDataResponse,
                    statisticsAverageMonthlySpending,
                    statisticsMaxMonthlySpending,
                    statisticsMaxMonthlySpendingItem,
                    statisticsMinMonthlySpendingItem)
            }
        })

        statisticsUsername.text = username

        statisticsHandler.getWeeklyEstimatedSpending(username, object: HandleStatisticsGeneration.EstimatedExpenseCallback {
            override fun onEstimatedExpenseCallback(response: String) {
                statisticsEstimatedSpending.text = response
            }
        })

        incomeFetch.fetchIncomeData(username, object: HandleIncomeCRUD.IncomeDataCallBack {
            override fun onIncomeDataReceived(incomeDataResponse: UserIncomeDataResponse) {
                val response = incomeDataResponse.income
                updateFinances(response, statisticsIncome)
            }
        })

        expenseFetch.fetchExpenseData(username, object: HandleExpenseCRUD.ExpenseDataCallBack {
            override fun onExpenseDataReceived(expenseDataResponse: UserExpenseDataResponse) {
                val response = expenseDataResponse.expense
                updateFinances(response, statisticsExpense)
            }
        })
        debtFetch.fetchDebtData(username, object: HandleDebtCRUD.DebtDataCallBack {
            override fun onDebtDataReceived(debtDataResponse: UserDebtDataResponse) {
                val response =  debtDataResponse.debt
                updateFinances(response, statisticsDebt)
            }
        })

        generateStatisticsButton = findViewById(R.id.generateStatisticsButton)
        generateStatisticsButton.setOnClickListener() {
            statisticsHandler.downloadStatistics(username, "statistics.pdf")
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(connectivityReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(connectivityReceiver)
    }

    override fun onConnectivityChange(isConnected: Boolean) {
        if (!(isConnected)) {
            networkChecker.checkConnectivity(this)
        }
    }

    private fun updateFinances(responseData: List<List<Any>>, textView: TextView) {
        var total = 0.0
        responseData.forEach { item ->
            val income = item[1].toString().toDouble()
            total += income
        }

        textView.text = String.format("%.2f", total)
    }

    fun calcWeeklySpending(
        statisticsDataResponse: StatisticsDataResponse,
        averageWeeklySpendingTextView: TextView,
        maxWeeklySpendingTextView: TextView,
        maxWeeklySpendingItemTextView: TextView,
        minWeeklySpendingItemTextView: TextView
    ) {
        val expenseStatistics = statisticsDataResponse.expense_statistics
        val weeklyExpenses = expenseStatistics.weekly_expense.expenses_week

        var totalWeeklyExpenses = 0.0
        var maxWeeklyExpenseItem: String? = null
        var minWeeklyExpenseItem: String? = null

        var maxWeeklyExpenseAmount = Double.MIN_VALUE
        var minWeeklyExpenseAmount = Double.MAX_VALUE

        for (weeklyExpense in weeklyExpenses) {
            totalWeeklyExpenses += weeklyExpense.total_amount

            for (expense in weeklyExpense.expense.expenses) {
                val amount = expense[1] as Double
                if (amount > maxWeeklyExpenseAmount) {
                    maxWeeklyExpenseAmount = amount
                    maxWeeklyExpenseItem = expense[0] as String
                }
                if (amount < minWeeklyExpenseAmount) {
                    minWeeklyExpenseAmount = amount
                    minWeeklyExpenseItem = expense[0] as String
                }
            }
        }

        val numberOfWeeks = weeklyExpenses.size

        val averageWeeklySpending = if (numberOfWeeks > 0) {
            totalWeeklyExpenses / numberOfWeeks
        } else {
            0.0
        }

        averageWeeklySpendingTextView.text = String.format("%.2f", averageWeeklySpending)
        maxWeeklySpendingTextView.text = String.format("%.2f", maxWeeklyExpenseAmount)
        maxWeeklySpendingItemTextView.text = maxWeeklyExpenseItem ?: "N/A"
        minWeeklySpendingItemTextView.text = minWeeklyExpenseItem ?: "N/A"

    }

    fun calcMonthlySpending(
        statisticsDataResponse: StatisticsDataResponse,
        averageMonthlySpendingTextView: TextView,
        maxMonthlySpendingTextView: TextView,
        maxMonthlySpendingItemTextView: TextView,
        minMonthlySpendingItemTextView: TextView
    ) {
        val expenseStatistics = statisticsDataResponse.expense_statistics
        val monthlyExpenses = expenseStatistics.monthly_expense.expenses_month

        var totalMonthlyExpenses = 0.0
        var maxMonthlyExpenseItem: String? = null
        var minMonthlyExpenseItem: String? = null

        var maxMonthlyExpenseAmount = Double.MIN_VALUE
        var minMonthlyExpenseAmount = Double.MAX_VALUE

        for (monthlyExpense in monthlyExpenses) {
            totalMonthlyExpenses += monthlyExpense.total_amount

            for (expense in monthlyExpense.expense.expenses) {
                val amount = expense[1] as Double
                if (amount > maxMonthlyExpenseAmount) {
                    maxMonthlyExpenseAmount = amount
                    maxMonthlyExpenseItem = expense[0] as String
                }
                if (amount < minMonthlyExpenseAmount) {
                    minMonthlyExpenseAmount = amount
                    minMonthlyExpenseItem = expense[0] as String
                }
            }
        }

        val numberOfMonths = monthlyExpenses.size

        val averageMonthlySpending = if (numberOfMonths > 0) {
            totalMonthlyExpenses / numberOfMonths
        } else {
            0.0
        }

        averageMonthlySpendingTextView.text = String.format("%.2f", averageMonthlySpending)
        maxMonthlySpendingTextView.text = String.format("%.2f", maxMonthlyExpenseAmount)
        maxMonthlySpendingItemTextView.text = maxMonthlyExpenseItem ?: "N/A"
        minMonthlySpendingItemTextView.text = minMonthlyExpenseItem ?: "N/A"
    }
}