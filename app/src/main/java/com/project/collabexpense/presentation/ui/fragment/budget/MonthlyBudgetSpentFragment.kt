package com.project.collabexpense.presentation.ui.fragment.budget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.collabexpense.databinding.FragmentMonthlyBudgetSpentBinding
import com.project.collabexpense.presentation.ui.fragment.budget.adapters.MonthlySpendAdapter
import com.project.collabexpense.presentation.viewmodel.BudgetViewModel
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MonthlyBudgetSpentFragment : Fragment() {

    private var _binding: FragmentMonthlyBudgetSpentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BudgetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMonthlyBudgetSpentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMonthlyBudgetSpends()

        lifecycleScope.launch {
            viewModel.monthlyBudget.collect {
                when (it) {
                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {

                        val adapter = MonthlySpendAdapter{

                        }
                        adapter.submitList(it.data)
                        binding.budgetRv.adapter = adapter
                    }
                }

            }
        }
    }
}

