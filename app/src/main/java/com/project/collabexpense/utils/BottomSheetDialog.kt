package com.project.collabexpense.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.collabexpense.databinding.BottomSheetStringListBinding
import com.project.collabexpense.presentation.ui.fragment.budget.adapters.MonthAdapter

class BottomSheetDialog(
    val list: List<String>
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

        binding.stringRv.adapter = adapter
        adapter.submitList(list)

    }
}