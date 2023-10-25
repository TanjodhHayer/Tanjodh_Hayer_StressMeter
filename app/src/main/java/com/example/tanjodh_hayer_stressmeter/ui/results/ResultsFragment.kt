package com.example.tanjodh_hayer_stressmeter.ui.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.data.Set
import com.example.tanjodh_hayer_stressmeter.R
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

data class StressData(val timestamp: Long, val stressLevel: Int)

class ResultsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_results, container, false)
        val anyChartView = view.findViewById<AnyChartView>(R.id.any_chart_view)


        val chart = AnyChart.line()
        chart.animation(true)
        chart.padding(10.0, 20.0, 5.0, 20.0)


        chart.title("A graph showing you Stress Levels")
        chart.xAxis(0).title("Instance")
        chart.yAxis(0).title("Stress Level")

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager


        val data = readStressScoresFromCSV()
        updateChart(data)

        val stressData = data.map { StressData(it.first, it.second) }

        val adapter = StressDataAdapter(stressData)
        recyclerView.adapter = adapter as RecyclerView.Adapter<*>


        val i = when {
            data.size < 5 -> 1.0
            data.size < 10 -> 1.0
            else -> 1.0
        }

        val scores = mutableListOf<DataEntry>()
        for ((index, entry) in data.withIndex()) {
            // Calculate the X-axis position based on the increment
            val xValue = index * i + 1.0

            scores.add(ValueDataEntry(xValue, entry.second))
        }

        val set = Set.instantiate()
        set.data(scores)

        val line = chart.line(set.mapAs("{ x: 'x', value: 'value' }"))
        line.name("Stress Score")
        line.markers().enabled(true)

        line.markers()
            .type(MarkerType.CIRCLE)
            .size(3.0)
            .fill("blue")
            .fill("blue")
        line.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        anyChartView.setChart(chart)
        return view
    }

    private fun readStressScoresFromCSV(): List<Pair<Long, Int>> {
        val stressScores = mutableListOf<Pair<Long, Int>>()

        val file = File(requireContext().filesDir, "stress_timestamp.csv")

        if (file.exists()) {
            try {
                val reader = BufferedReader(FileReader(file))
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    val parts = line?.split(",")
                    if (parts?.size == 2) {
                        val timestamp = parts[0].toLong()
                        val score = parts[1].toInt()
                        stressScores.add(Pair(timestamp, score))
                    }
                }

                reader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return stressScores
    }

    private fun updateChart(seriesData: List<Pair<Long, Int>>) {
        val dataEntries = mutableListOf<DataEntry>()
        for ((index, entry) in seriesData.withIndex()) {
            val xValue = calculateXValue(index)
            dataEntries.add(ValueDataEntry(xValue, entry.second))
        }

    }
    private fun calculateXValue(index: Int): Double {
        return index * 0.2
    }
}
