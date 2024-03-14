package com.example.budgetbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.budgetbuddy.DataClasses.StatisticsData.StatisticsDataResponse
import com.example.budgetbuddy.Handlers.StatisticsGenerationHandler.HandleStatisticsGeneration

class GeneralStatisticsActivity : AppCompatActivity() {

    private lateinit var generateStatisticsButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_statistics)

        // Handling normal user statistics
        val x = HandleStatisticsGeneration(this)
        x.getExpenseStatistics("Tamás Bakcsi", object: HandleStatisticsGeneration.StatisticsDataCallBack {
            override fun onStatisticsDataReceived(statisticsDataResponse: StatisticsDataResponse?) {
                Log.d("GeneralStatisticsActivity", statisticsDataResponse.toString())
            }
        })


        // Statistics generation
        val statisticsHandler = HandleStatisticsGeneration(this)
        val username: String = intent.getStringExtra("USERNAME").toString()

        generateStatisticsButton = findViewById(R.id.generateStatisticsButton)

        generateStatisticsButton.setOnClickListener() {
            statisticsHandler.downloadStatistics(username, "statistics.pdf")
        }
    }
}