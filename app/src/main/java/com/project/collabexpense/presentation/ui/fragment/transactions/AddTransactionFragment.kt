package com.project.collabexpense.presentation.ui.fragment.transactions

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.project.collabexpense.databinding.FragmentAddTransactionBinding
import com.project.collabexpense.presentation.viewmodel.TransactionViewModel
import com.project.collabexpense.utils.BottomSheetDialog
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class AddTransactionFragment : Fragment() {

    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionViewModel by viewModels()

    private var categoryList: List<String> = emptyList()

    private var transactionDate = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddTransactionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            categoryEt.setOnClickListener {
                showCategoriesPicker()
            }

            dateEt.setOnClickListener {
                showDatePickerDialog { dateInMillis, formattedDate ->
                    // Update the TextView with the selected date
                    transactionDate = dateInMillis
                    dateEt.setText(formattedDate)
                }
            }

            addTransactionBtn.setOnClickListener {
                lifecycleScope.launch {
                    val jsonParams: MutableMap<String?, Any?> = ArrayMap()
                    jsonParams["category"] = binding.categoryEt.text.toString()
                    jsonParams["amount"] = binding.amountEt.text.toString()
                    jsonParams["transactionDate"] = transactionDate


                    val body = JSONObject(jsonParams).toString()
                        .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                    viewModel.postTransaction(body)

                    viewModel.addedTransaction.collect {
                        when (it) {
                            is Resource.Error -> {

                            }

                            is Resource.Loading -> {

                            }

                            is Resource.Success -> {
                                findNavController().popBackStack()
                            }
                        }
                    }
                }
            }

        }

        viewModel.getCategories()

        lifecycleScope.launch {
            viewModel.categoriesList.collect{
                when (it){
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {

                        if (it.data?.isNotEmpty() == true) {
                            categoryList = it.data
                        }

                        Log.d("TAG", "onViewCreated: "+it.data)
                    }
                }
            }
        }

    }

    private fun showDatePickerDialog(onDateSet: (dateInMillis: Long, formattedDate: String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            // Create a Calendar object and set it to the selected date
            val selectedDate = Calendar.getInstance()
            selectedDate.set(selectedYear, selectedMonth, selectedDay)

            // Get the date in milliseconds
            val dateInMillis = selectedDate.timeInMillis

            // Format the date to a human-readable string
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)

            // Pass the date in milliseconds and formatted date to the onDateSet callback
            onDateSet(dateInMillis, formattedDate)
        }, year, month, day).show()
    }

    private fun showCategoriesPicker() {

        val bottomSheetFragment = BottomSheetDialog(categoryList)
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)

        bottomSheetFragment.stringData.observe(viewLifecycleOwner) {
            binding.categoryEt.setText(it.toString())
        }

    }
}
