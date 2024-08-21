package com.project.collabexpense.presentation.ui.fragment.groups

import android.os.Bundle
import android.text.InputType
import android.util.ArrayMap
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.project.collabexpense.R
import com.project.collabexpense.databinding.FragmentCreateGroupBinding
import com.project.collabexpense.databinding.FragmentMyGroupsBinding
import com.project.collabexpense.presentation.viewmodel.GroupViewModel
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

@AndroidEntryPoint
class CreateGroupFragment : Fragment() {

    private var _binding: FragmentCreateGroupBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GroupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCreateGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddEmail.setOnClickListener {
            addEmailField()
        }

        binding.btnSubmit.setOnClickListener {
            submitGroup()
        }
    }

    private fun addEmailField() {
        val emailField = EditText(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            hint = "Enter email"
            inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
        binding.emailsContainer.addView(emailField)
    }

    private fun submitGroup() {
        val groupName = binding.etGroupName.text.toString()
        val userEmails = mutableListOf<String>()

        for (i in 0 until binding.emailsContainer.childCount) {
            val emailField = binding.emailsContainer.getChildAt(i) as EditText
            val email = emailField.text.toString()
            if (email.isNotEmpty()) {
                userEmails.add(email)
            }
        }

        lifecycleScope.launch {
            val jsonParams: MutableMap<String?, Any?> = ArrayMap()
            jsonParams["name"] = groupName

            jsonParams["userEmails"] = userEmails

            val body = JSONObject(jsonParams).toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            viewModel.createGroup(body)

            viewModel.userGroupCreated.collect{
                when (it){
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