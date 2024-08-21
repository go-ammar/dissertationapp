package com.project.collabexpense.presentation.ui.fragment.budget.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.project.collabexpense.R
import com.project.collabexpense.base.BaseAdapter
import com.project.collabexpense.databinding.ItemMonthBinding

class MonthAdapter
    (
    private val onClickListener: (model: String) -> Unit,
) : BaseAdapter<String>(

    diffCallback = object : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_month, parent, false
        )
    }

    override fun bind(binding: ViewDataBinding, item: String, position: Int) {
        when (binding) {
            is ItemMonthBinding -> binding.apply {
                monthTv.text = item

                monthTv.setOnClickListener {
                    onClickListener.invoke(item)
                }

            }
        }
    }


}