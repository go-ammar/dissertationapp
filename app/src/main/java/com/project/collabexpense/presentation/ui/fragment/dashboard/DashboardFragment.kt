package com.project.collabexpense.presentation.ui.fragment.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.project.collabexpense.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.headingTv.text = "Welcome back!"

        APIlib.getInstance().setActiveAnyChartView(binding.pieChart)

        val pie = AnyChart.pie3d()
        pie.title("Expense by category")

        val data: MutableList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("Grocery", 10000))
        data.add(ValueDataEntry("Bills", 12000))
        data.add(ValueDataEntry("Take out", 18000))
        pie.data(data)
        pie.animation().enabled(true).duration(1000)
        binding.pieChart.setChart(pie)

        APIlib.getInstance().setActiveAnyChartView(binding.lineChart)

        val line = AnyChart.area3d()
        line.title("Monthly Expense")

        val dataForLine: MutableList<DataEntry> = ArrayList()
        dataForLine.add(ValueDataEntry("March", 10000))
        dataForLine.add(ValueDataEntry("April", 12000))
        dataForLine.add(ValueDataEntry("May", 18000))

        line.data(dataForLine)
        line.animation().enabled(true).duration(1000)
        binding.lineChart.setChart(line)
    }
}