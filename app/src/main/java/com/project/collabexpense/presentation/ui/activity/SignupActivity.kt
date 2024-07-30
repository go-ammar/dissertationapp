package com.project.collabexpense.presentation.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.ArrayMap
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.collabexpense.base.BaseActivity
import com.project.collabexpense.databinding.ActivitySignupBinding
import com.project.collabexpense.presentation.viewmodel.LoginViewModel
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class SignupActivity : BaseActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel: LoginViewModel by viewModels()

    private var dob = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionViews()
    }

    private fun actionViews() {

        binding.loginButton.setOnClickListener {
            val jsonParams: MutableMap<String?, Any?> = ArrayMap()
            jsonParams["email"] = binding.emailEt.text.toString()
            jsonParams["name"] = binding.usernameEt.text.toString()
            jsonParams["dob"] = dob

            jsonParams["password"] = binding.passwordEt.text.toString()

            val body = JSONObject(jsonParams).toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            lifecycleScope.launch {
                viewModel.signUpAuth(body)

                viewModel.dataSignup.collect {
                    when (it) {

                        is Resource.Error -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                prefsManager.clearPreferences()
                            }
                            prefsHelper.clearPreferences()
                        }

                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }



        binding.dobEt.setOnClickListener {
            showDatePickerDialog { dateInMillis, formattedDate ->
                // Update the TextView with the selected date
                dob = dateInMillis
                binding.dobEt.setText(formattedDate)
            }
        }


    }

    private fun showDatePickerDialog(onDateSet: (dateInMillis: Long, formattedDate: String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
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