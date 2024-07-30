package com.project.collabexpense.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.collabexpense.R
import com.project.collabexpense.databinding.BottomSheetStringListBinding
import com.project.collabexpense.presentation.ui.fragment.budget.MonthAdapter

class BottomSheetDialog(
) : BottomSheetDialogFragment() {

    var stringData = MutableLiveData<String>()


    private var _binding: BottomSheetStringListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = BottomSheetStringListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MonthAdapter {
            stringData.postValue(it)
            dismiss()
        }

        binding.monthRv.adapter = adapter

        val months = listOf(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
        )

        adapter.submitList(months)

    }
}