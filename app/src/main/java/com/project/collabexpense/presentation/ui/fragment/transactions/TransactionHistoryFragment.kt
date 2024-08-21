package com.project.collabexpense.presentation.ui.fragment.transactions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.project.collabexpense.databinding.FragmentTransactionHistoryBinding
import com.project.collabexpense.presentation.ui.fragment.transactions.adapters.TransactionHistoryAdapter
import com.project.collabexpense.presentation.viewmodel.TransactionViewModel
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransactionHistoryFragment : Fragment() {

    private var _binding: FragmentTransactionHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTransactionHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {

            addFab.setOnClickListener {
                val action = TransactionHistoryFragmentDirections.actionTransactionHistoryFragmentToAddTransactionFragment()
                findNavController().navigate(action)
            }

        }

        viewModel.getTransactions()

        lifecycleScope.launch {
            viewModel.transactionList.collect{
                when (it){
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        val adapter =  TransactionHistoryAdapter {

                        }

                        adapter.submitList(it.data)
                        binding.transactionsRv.adapter = adapter

                        Log.d("TAG", "onViewCreated: "+it.data)
                    }
                }
            }
        }

    }
}