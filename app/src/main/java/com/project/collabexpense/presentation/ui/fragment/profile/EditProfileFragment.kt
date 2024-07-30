package com.project.collabexpense.presentation.ui.fragment.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.collabexpense.data.local.prefs.PreferenceHelper
import com.project.collabexpense.databinding.FragmentEditProfileBinding
import com.project.collabexpense.presentation.viewmodel.EditProfileViewModel
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private val viewModel: EditProfileViewModel by viewModels()
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val args: EditProfileFragmentArgs by navArgs()

    private var dob = 0L
    private var email = ""
    private var name = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (args.password) {
            binding.passwordEt.visibility = View.VISIBLE
            binding.confirmPasswordEt.visibility = View.VISIBLE
            binding.prevPasswordEt.visibility = View.VISIBLE
        }

        if (args.dobChange) {
            binding.dobEt.visibility = View.VISIBLE

            binding.dobEt.setOnClickListener {
                showDatePickerDialog { dateInMillis, formattedDate ->
                    // Update the TextView with the selected date
                    dob = dateInMillis
                    binding.dobEt.setText(formattedDate)
                }
            }
        }

        if (args.nameChange) {
            binding.usernameEt.visibility = View.VISIBLE
        }

        binding.saveButton.setOnClickListener {
            ProfileFragment.update.value = true
//            if (args.password) {
//                binding.passwordEt.visibility = View.VISIBLE
//                binding.confirmPasswordEt.visibility = View.VISIBLE
//                binding.prevPasswordEt.visibility = View.VISIBLE
//            }
            val jsonParams: MutableMap<String?, Any?> = ArrayMap()

            if (args.dobChange) {
                jsonParams["dob"] = dob
            } else {
                jsonParams["dob"] = args.dob
            }

            if (args.nameChange) {
                jsonParams["name"] = binding.usernameEt.text.toString()
            } else {
                jsonParams["name"] = args.name
            }

            jsonParams["email"] = args.email

            val body = JSONObject(jsonParams).toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            lifecycleScope.launch {
                viewModel.updateUserDetails(body)

                viewModel.updatedUser.collect {
                    when (it) {
                        is Resource.Loading -> {
                            // Show loading state
//                        binding.progressBar.visibility = View.VISIBLE
                        }

                        is Resource.Success -> {
                            if (it.data?.updatedToken != ""){
                                PreferenceHelper(requireContext()).authToken =
                                    it.data?.updatedToken.toString()
                            }
                            findNavController().popBackStack()
                        }

                        is Resource.Error -> {
                            // Show error message
                            Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
                        }
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
}