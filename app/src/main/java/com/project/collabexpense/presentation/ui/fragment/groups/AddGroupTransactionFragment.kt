package com.project.collabexpense.presentation.ui.fragment.groups

import android.os.Bundle
import android.text.InputType
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.collabexpense.databinding.FragmentAddGroupTransactionBinding
import com.project.collabexpense.presentation.viewmodel.GroupViewModel
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


@AndroidEntryPoint
class AddGroupTransactionFragment : Fragment() {


    private var _binding: FragmentAddGroupTransactionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GroupViewModel by viewModels()

    private val args: AddGroupTransactionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddGroupTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addUserShareButton.setOnClickListener {
            // Create a new LinearLayout for a user share entry
            val userShareLayout = LinearLayout(requireContext())
            userShareLayout.orientation = LinearLayout.HORIZONTAL

            // Create EditText for user email
            val emailInput = EditText(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
                hint = "Enter email"
                inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }

            // Create EditText for share amount
            val shareInput = EditText(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
                hint = "Enter share amount"
                inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            }

            // Add email and share input fields to the userShareLayout
            userShareLayout.addView(emailInput)
            userShareLayout.addView(shareInput)

            // Add the new userShareLayout to the userSharesContainer
            binding.userSharesContainer.addView(userShareLayout)
        }

        binding.submitButton.setOnClickListener {
            // Get the description
            val description = binding.descriptionInput.text.toString()

            // Get the amount paid
            val amountPaid = binding.amountPaidInput.text.toString().toDoubleOrNull() ?: 0.0

            // Get the paidByUserEmail
            val paidByUserEmail = binding.paidByUserEmailInput.text.toString()

            // Initialize a map to store user shares
            val userShares = mutableMapOf<String, Int>()

            // Iterate over the userSharesContainer children to extract user emails and share amounts
            for (i in 0 until binding.userSharesContainer.childCount) {
                val userShareLayout = binding.userSharesContainer.getChildAt(i) as LinearLayout

                // Extract email and share amount from each user share entry
                val emailInput = userShareLayout.getChildAt(0) as EditText
                val shareAmountInput = userShareLayout.getChildAt(1) as EditText

                val userEmail = emailInput.text.toString()
                val shareAmount = shareAmountInput.text.toString().toIntOrNull() ?: 0

                if (userEmail.isNotEmpty()) {
                    userShares[userEmail] = shareAmount
                }
            }

            val jsonParams: MutableMap<String?, Any?> = ArrayMap()
            jsonParams["description"] = description
            jsonParams["transactionDate"] = System.currentTimeMillis()
            jsonParams["amountPaid"] = amountPaid
            jsonParams["groupId"] = args.groupId
            jsonParams["paidByUserEmail"] = paidByUserEmail
            jsonParams["userShares"] = userShares


            val body = JSONObject(jsonParams).toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())


            viewModel.addTransaction(body)

            lifecycleScope.launch {
                viewModel.groupTransactionAdded.collect {
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