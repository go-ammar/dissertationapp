package com.project.collabexpense.presentation.ui.fragment.budget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.project.collabexpense.R
import com.project.collabexpense.base.BaseAdapter
import com.project.collabexpense.data.remote.models.Budget
import com.project.collabexpense.databinding.ItemBudgetListBinding
import com.project.collabexpense.domain.model.MyData

class BudgetAdapter(
    private val onClickListener: (model: MyData) -> Unit,
) : BaseAdapter<Budget>(

    diffCallback = object : DiffUtil.ItemCallback<Budget>() {

        override fun areItemsTheSame(oldItem: Budget, newItem: Budget): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Budget, newItem: Budget): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_budget_list,
            parent,
            false
        )
    }

    override fun bind(binding: ViewDataBinding, item: Budget, position: Int) {
        when (binding) {
            is ItemBudgetListBinding ->
                binding.apply {
                    categoryTv.text = item.category
                    spentTv.text = "Spent £0"
                    limitTv.text = item.amount.toString()
                    progressBar.progress = 0
                }
        }
    }

}