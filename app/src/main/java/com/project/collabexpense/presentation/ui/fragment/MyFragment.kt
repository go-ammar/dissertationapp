package com.project.collabexpense.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.collabexpense.databinding.FragmentMyBinding
import com.project.collabexpense.presentation.viewmodel.MyFragmentViewModel
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyFragment : Fragment() {

    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe data from the ViewModel
        lifecycleScope.launch {
            viewModel.data.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Show loading state
//                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        // Show data
//                        binding.progressBar.visibility = View.GONE
                        // Update your UI with the data
                        val data = resource.data
                        // TODO: Use your adapter or any view to display the data
                    }

                    is Resource.Error -> {
                        // Show error message
//                        binding.progressBar.visibility = View.GONE
//                        binding.errorTextView.visibility = View.VISIBLE
//                        binding.errorTextView.text = resource.message
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}