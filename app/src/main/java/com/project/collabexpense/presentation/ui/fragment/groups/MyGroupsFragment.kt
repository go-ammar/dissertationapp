package com.project.collabexpense.presentation.ui.fragment.groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.collabexpense.R
import com.project.collabexpense.databinding.FragmentDashboardBinding
import com.project.collabexpense.databinding.FragmentMyGroupsBinding
import com.project.collabexpense.presentation.viewmodel.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MyGroupsFragment : Fragment() {

    private var _binding: FragmentMyGroupsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GroupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMyGroupsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getUserGroups()
        }
    }

}