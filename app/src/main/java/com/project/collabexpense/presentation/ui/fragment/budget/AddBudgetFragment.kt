package com.project.collabexpense.presentation.ui.fragment.budget

import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.project.collabexpense.databinding.FragmentAddBudgetBinding
import com.project.collabexpense.presentation.viewmodel.BudgetViewModel
import com.project.collabexpense.utils.BottomSheetDialog
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


@AndroidEntryPoint
class AddBudgetFragment : Fragment() {

    private var _binding: FragmentAddBudgetBinding? = null
    private val binding get() = _binding!!

    private val budgetViewModel: BudgetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addBudgetButton.setOnClickListener {

            lifecycleScope.launch {
                val jsonParams: MutableMap<String?, Any?> = ArrayMap()
                jsonParams["category"] = binding.categoryEt.text.toString()
                jsonParams["amount"] = binding.amountEt.text.toString()

                val body = JSONObject(jsonParams).toString()
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                budgetViewModel.addBudgets(body)

                budgetViewModel.addedBudget.collect {
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

}