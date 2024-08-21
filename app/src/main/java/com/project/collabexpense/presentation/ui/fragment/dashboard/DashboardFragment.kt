package com.project.collabexpense.presentation.ui.fragment.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.project.collabexpense.data.remote.models.MonthlyCategorySpend
import com.project.collabexpense.databinding.FragmentDashboardBinding
import com.project.collabexpense.presentation.viewmodel.BudgetViewModel
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar


@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BudgetViewModel by viewModels()

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



        viewModel.getMonthlyBudgetSpends()

        lifecycleScope.launch {
            viewModel.monthlyBudget.collect {
                when (it) {
                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {

                        val calendar = Calendar.getInstance()
                        val currentMonth = calendar.get(Calendar.MONTH) + 1 // Months are indexed from 0
                        val currentYear = calendar.get(Calendar.YEAR)

                        val expenses = it.data
                        var filteredExpenses = expenses?.filter { expense -> expense.month == currentMonth && expense.year == currentYear }

                        if (filteredExpenses?.isNotEmpty() == true) {
                            // If no expenses for the current month, find the most recent month's expenses
                            val sortedExpenses = expenses!!.sortedWith(compareByDescending<MonthlyCategorySpend> { spend -> spend.year }.thenByDescending { spend -> spend.month })
                            val mostRecentMonth = sortedExpenses.firstOrNull()?.month ?: currentMonth
                            val mostRecentYear = sortedExpenses.firstOrNull()?.year ?: currentYear

                            filteredExpenses = expenses.filter { it.month == mostRecentMonth && it.year == mostRecentYear }


                            // Now, populate the pie chart with the filtered expenses
                            APIlib.getInstance().setActiveAnyChartView(binding.pieChart)

                            val pie = AnyChart.pie3d()
                            pie.title("Expense by Category")

                            val pieData: MutableList<DataEntry> = ArrayList()
                            for (expense in filteredExpenses) {
                                pieData.add(ValueDataEntry(expense.category, expense.totalAmount))
                            }

                            pie.data(pieData)
                            pie.animation().enabled(true).duration(1000)
                            binding.pieChart.setChart(pie)
                        }

                        APIlib.getInstance().setActiveAnyChartView(binding.lineChart)
                        val line = AnyChart.area3d()
                        line.title("Monthly Expense")

                        val monthlyTotals = expenses?.groupBy { it.year to it.month }
                            ?.map { (yearMonth, expenseList) ->
                                val totalAmount = expenseList.sumOf { it.totalAmount }
                                val monthName = getMonthName(yearMonth.second)
                                "$monthName ${yearMonth.first}" to totalAmount
                            }

                        val dataForLine: MutableList<DataEntry> = ArrayList()
                        if (monthlyTotals != null) {
                            for ((monthYear, totalAmount) in monthlyTotals) {
                                dataForLine.add(ValueDataEntry(monthYear, totalAmount))
                            }
                        }

                        line.data(dataForLine)
                        line.animation().enabled(true).duration(1000)

// Set chart to the view
                        binding.lineChart.setChart(line)

                    }
                }

            }
        }


//        APIlib.getInstance().setActiveAnyChartView(binding.pieChart)
//
//        val pie = AnyChart.pie3d()
//        pie.title("Expense by category")
//
//        val data: MutableList<DataEntry> = ArrayList()
//        data.add(ValueDataEntry("Grocery", 10000))
//        data.add(ValueDataEntry("Bills", 12000))
//        data.add(ValueDataEntry("Take out", 18000))
//        pie.data(data)
//        pie.animation().enabled(true).duration(1000)
//        binding.pieChart.setChart(pie)
//
//        APIlib.getInstance().setActiveAnyChartView(binding.lineChart)
//
//        val line = AnyChart.area3d()
//        line.title("Monthly Expense")
//
//        val dataForLine: MutableList<DataEntry> = ArrayList()
//        dataForLine.add(ValueDataEntry("March", 10000))
//        dataForLine.add(ValueDataEntry("April", 12000))
//        dataForLine.add(ValueDataEntry("May", 18000))
//
//        line.data(dataForLine)
//        line.animation().enabled(true).duration(1000)
//        binding.lineChart.setChart(line)
    }

    fun getMonthName(month: Int): String {
        return when (month) {
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "July"
            8 -> "August"
            9 -> "September"
            10 -> "October"
            11 -> "November"
            12 -> "December"
            else -> ""
        }
    }
}