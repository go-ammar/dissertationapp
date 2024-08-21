package com.project.collabexpense.presentation.ui.fragment.groups.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.project.collabexpense.R
import com.project.collabexpense.base.BaseAdapter
import com.project.collabexpense.data.remote.models.GroupData
import com.project.collabexpense.data.remote.models.Transactions
import com.project.collabexpense.databinding.ItemGroupDetailsUsersBinding

class GroupDetailsUsersAdapter (
    private val onClickListener: (model: GroupData) -> Unit,
) : BaseAdapter<Transactions>(

    diffCallback = object : DiffUtil.ItemCallback<Transactions>() {

        override fun areItemsTheSame(oldItem: Transactions, newItem: Transactions): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Transactions, newItem: Transactions): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_group_details_transactions,
            parent,
            false
        )
    }

    override fun bind(binding: ViewDataBinding, item: Transactions, position: Int) {
        when (binding) {
            is ItemGroupDetailsUsersBinding ->
                binding.apply {

                    card.setOnClickListener {
//                        onClickListener.invoke(item)
                    }
                }
        }
    }

}