package com.project.collabexpense.presentation.ui.fragment.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.collabexpense.databinding.FragmentGroupDetailsBinding
import com.project.collabexpense.presentation.viewmodel.GroupViewModel
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GroupDetailsFragment : Fragment() {


    private var _binding: FragmentGroupDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GroupViewModel by viewModels()

    private val args: GroupDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGroupDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getGroupDetails(args.groupId)

        lifecycleScope.launch {

            viewModel.groupDetails.collect {
                when (it) {
                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        binding.groupName.text = "Group name: " + it.data?.name

                        val adapter = GroupDetailTransactionAdapter { transaction ->
                            val action =
                                GroupDetailsFragmentDirections.actionGroupDetailsFragmentToGroupTransactionDetailFragment(
                                    transaction
                                )
                            findNavController().navigate(action)
                        }

                        adapter.submitList(it.data?.transactions)
                        binding.transactionsRv.adapter = adapter

                    }
                }
            }
        }


        binding.addFab.setOnClickListener {
            val action =
                GroupDetailsFragmentDirections.actionGroupDetailsFragmentToAddGroupTransactionFragment(
                    args.groupId
                )
            findNavController().navigate(action)
        }

    }
}