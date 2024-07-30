package com.project.collabexpense.presentation.ui.fragment.budget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.project.collabexpense.R
import com.project.collabexpense.databinding.FragmentBudgetBinding
import com.project.collabexpense.domain.model.MyData
import com.project.collabexpense.presentation.viewmodel.BudgetViewModel
import com.project.collabexpense.presentation.viewmodel.MyFragmentViewModel
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BudgetFragment : Fragment() {

    private var _binding: FragmentBudgetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyFragmentViewModel by viewModels()
    private val budgetViewModel: BudgetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBudgetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val items = listOf(
            MyData("1", "Item 1"),
            MyData("2", "Item 2"),
            MyData("3", "Item 3")
        )

        val adapter = BudgetAdapter {

        }

        binding.budgetRv.adapter = adapter




        lifecycleScope.launch {
            budgetViewModel.getBudgets()

            budgetViewModel.budget.collect {
                when (it){
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        adapter.submitList(it.data)
                    }
                }
            }
        }

        binding.addFab.setOnClickListener {
            findNavController().navigate(R.id.addBudgetFragment)
        }
    }
}