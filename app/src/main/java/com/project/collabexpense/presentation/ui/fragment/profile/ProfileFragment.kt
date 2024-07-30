package com.project.collabexpense.presentation.ui.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.project.collabexpense.databinding.FragmentProfileBinding
import com.project.collabexpense.presentation.ui.activity.DashboardActivity
import com.project.collabexpense.presentation.ui.activity.LoginActivity
import com.project.collabexpense.presentation.viewmodel.EditProfileViewModel
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: EditProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

//    private var dob: Long? = null
//    private var email: String = ""

    companion object {
        var email = ""
        var dob = 0L
        var name = ""
        var update: MutableLiveData<Boolean> = MutableLiveData(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserDetails()

        lifecycleScope.launch {
            viewModel.data.collect {
                when (it) {
                    is Resource.Loading -> {
                        // Show loading state
//                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        // Show data
//                        binding.progressBar.visibility = View.GONE
                        // Update your UI with the data

                        val date = it.data?.dob?.let { it1 -> Date(it1) }
                        val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

                        email = it.data?.email.toString()
                        dob = it.data?.dob!!
                        name = it.data.name

                        binding.nameTv.text = "Name: " + it.data.name.toString()
                        binding.dobTv.text = "Date of Birth: " + format.format(date)
                    }

                    is Resource.Error -> {
                        // Show error message
                        Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.cvName.setOnClickListener {
            val action =
                ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(
                    name = name,
                    email = email,
                    dob = dob,
                    nameChange = true
                )
            findNavController().navigate(action)
        }

        binding.cvDob.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(
                    dob = dob,
                    name = name,
                    email = email,
                    dobChange = true,
                nameChange = false
                )

            findNavController().navigate(action)
        }

        binding.cvPassword.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(
                password = true,
                name = null,
                email = email,
                nameChange = false,
                dobChange = false

            )
            findNavController().navigate(action)
        }

        binding.saveButton.setOnClickListener {
            val jsonParams: MutableMap<String?, Any?> = ArrayMap()
            jsonParams["email"] = email
            jsonParams["name"] = name
            jsonParams["dob"] = dob

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
                            val date = it.data?.dob?.let { it1 -> Date(it1) }
                            val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                            email = it.data?.email.toString()
                            dob = it.data?.dob!!
                            name = it.data.name

                            binding.nameTv.text = "Name: " + it.data.name.toString()
                            binding.dobTv.text = "Date of Birth: " + format.format(date)
                        }

                        is Resource.Error -> {
                            // Show error message
                            Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }


        }

        binding.logoutBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                (activity as DashboardActivity).prefsManager.clearPreferences()
            }
            (activity as DashboardActivity).prefsHelper.clearPreferences()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            (activity as DashboardActivity).finish()
        }

        update.observe(viewLifecycleOwner) {
            if (it) {
                binding.saveButton.alpha = 1f

                val date = Date(dob)
                val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                binding.nameTv.text = "Name: " + name
                binding.dobTv.text = "Date of Birth: " + format.format(date)

            } else {
                binding.saveButton.alpha = 0.4f
            }
            binding.saveButton.isEnabled = it
        }
    }
}