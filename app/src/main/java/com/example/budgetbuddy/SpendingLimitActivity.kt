package com.example.budgetbuddy

import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.budgetbuddy.DataClasses.SpendingLimitData.SpendingLimitResponse
import com.example.budgetbuddy.DataClasses.StatisticsData.StatisticsDataResponse
import com.example.budgetbuddy.DataClasses.UserData.UserExpenseDataResponse
import com.example.budgetbuddy.Handlers.FiancesHandler.HandleExpenseCRUD
import com.example.budgetbuddy.Handlers.SpendingLimitHandler.HandleSpendingLimit
import com.example.budgetbuddy.Handlers.StatisticsGenerationHandler.HandleStatisticsGeneration

class SpendingLimitActivity : AppCompatActivity() {
    private val spendingLimitHandler = HandleSpendingLimit()
    private val expenseHandler = HandleExpenseCRUD(this)
    private val statisticsHandler = HandleStatisticsGeneration(this)

    private lateinit var weeklySpendingLimitProgressBar: ProgressBar
    private lateinit var weeklyProgressCounter: TextView
    private lateinit var weeklySpendingLimitAmount: EditText
    private lateinit var setWeeklySpendingLimit: androidx.appcompat.widget.AppCompatButton

    private lateinit var monthlySpendingLimitProgressBar: ProgressBar
    private lateinit var monthlyProgressCounter: TextView
    private lateinit var monthlySpendingLimitAmount: EditText
    private lateinit var setMonthlySpendingLimit: androidx.appcompat.widget.AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spending_limit)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        weeklySpendingLimitProgressBar = findViewById(R.id.weeklySpendingLimitProgressBar)
        weeklyProgressCounter = findViewById(R.id.weeklyProgressCounter)
        weeklySpendingLimitAmount = findViewById(R.id.weeklySpendingLimitAmount)
        setWeeklySpendingLimit = findViewById(R.id.setWeeklySpendingLimit)

        monthlySpendingLimitProgressBar = findViewById(R.id.monthlySpendingLimitProgressBar)
        monthlyProgressCounter = findViewById(R.id.monthlyProgressCounter)
        monthlySpendingLimitAmount = findViewById(R.id.monthlySpendingLimitAmount)
        setMonthlySpendingLimit = findViewById(R.id.setMonthlySpendingLimit)

        val username = intent.getStringExtra("USERNAME").toString()

        expenseHandler.fetchExpenseData(username, object: HandleExpenseCRUD.ExpenseDataCallBack {
            override fun onExpenseDataReceived(expenseDataResponse: UserExpenseDataResponse) {
                val totalExpense = calcTotalExpense(expenseDataResponse.expense).toInt()

                spendingLimitHandler.fetchWeeklySpendingLimit(username, object: HandleSpendingLimit.onSpendingLimitDataReceived {
                    override fun onSpendingLimitDataReceived(spendingLimitDataResponse: SpendingLimitResponse) {
                        val weeklySpendingLimit = spendingLimitDataResponse.response.toString().toInt()

                        calcWeeklyProgressBarValue(totalExpense, weeklySpendingLimit, weeklySpendingLimitProgressBar)
                    }
                })
            }
        })

        statisticsHandler.getExpenseStatistics(username, object: HandleStatisticsGeneration.StatisticsDataCallBack {
            override fun onStatisticsDataReceived(statisticsDataResponse: StatisticsDataResponse?) {
                val totalMonthlyExpense = statisticsDataResponse!!.expense_statistics.monthly_expense.total_amount

                spendingLimitHandler.fetchMonthlySpendingLimit(username, object: HandleSpendingLimit.onSpendingLimitDataReceived {
                    override fun onSpendingLimitDataReceived(spendingLimitDataResponse: SpendingLimitResponse) {
                        val monthlySpendingLimit = spendingLimitDataResponse.response.toString().toInt()

                        calcMonthlyProgressBarValue(totalMonthlyExpense, monthlySpendingLimit, monthlySpendingLimitProgressBar)
                    }
                })
            }
        })

        setWeeklySpendingLimit.setOnClickListener {
            val weeklySpendingLimitAmountDouble = weeklySpendingLimitAmount.text.toString().toInt()
            spendingLimitHandler.saveWeeklySpendingLimit(username, weeklySpendingLimitAmountDouble)
        }

        setMonthlySpendingLimit.setOnClickListener {
            val monthlySpendingLimitAmountDouble = monthlySpendingLimitAmount.text.toString().toInt()
            spendingLimitHandler.saveMonthlySpendingLimit(username, monthlySpendingLimitAmountDouble)
        }
    }

    fun calcWeeklyProgressBarValue(totalExpense: Int, spendingLimit: Int, progressBar: ProgressBar) {
        val progressBarValue = (totalExpense.toDouble() / spendingLimit.toDouble()) * 100
        progressBar.progress = progressBarValue.toInt()
        weeklyProgressCounter.text = "$totalExpense out of $spendingLimit"
    }

    fun calcMonthlyProgressBarValue(totalExpense: Int, spendingLimit: Int, progressBar: ProgressBar) {
        val progressBarValue = (totalExpense.toDouble() / spendingLimit.toDouble()) * 100
        progressBar.progress = progressBarValue.toInt()
        monthlyProgressCounter.text = "$totalExpense out of $spendingLimit"
    }


    fun calcTotalExpense(expenseData: List<List<Any>>): Double {
        var totalExpense = 0.0
        expenseData.forEach { item ->
            totalExpense += item[1].toString().toDouble()
        }
        return totalExpense
    }
}