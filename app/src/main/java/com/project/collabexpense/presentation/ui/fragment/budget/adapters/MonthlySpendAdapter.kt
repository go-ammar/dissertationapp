package com.project.collabexpense.presentation.ui.fragment.budget.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.project.collabexpense.R
import com.project.collabexpense.base.BaseAdapter
import com.project.collabexpense.data.remote.models.MonthlyCategorySpend
import com.project.collabexpense.databinding.ItemMonthlySpentBinding
import com.project.collabexpense.domain.model.MyData

class MonthlySpendAdapter(
    private val onClickListener: (model: MyData) -> Unit,
) : BaseAdapter<MonthlyCategorySpend>(

    diffCallback = object : DiffUtil.ItemCallback<MonthlyCategorySpend>() {

        override fun areItemsTheSame(
            oldItem: MonthlyCategorySpend,
            newItem: MonthlyCategorySpend
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: MonthlyCategorySpend,
            newItem: MonthlyCategorySpend
        ): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_monthly_spent,
            parent,
            false
        )
    }

    override fun bind(binding: ViewDataBinding, item: MonthlyCategorySpend, position: Int) {
        when (binding) {
            is ItemMonthlySpentBinding ->
                binding.apply {
                    categoryTv.text = item.category
                    spentTv.text = "Spent £" + item.totalAmount.toString()
                    limitTv.text = "£" + item.budgetAmount.toString()
                    progressBar.progress = (item.totalAmount / item.budgetAmount * 100).toInt()
                }
        }
    }

}