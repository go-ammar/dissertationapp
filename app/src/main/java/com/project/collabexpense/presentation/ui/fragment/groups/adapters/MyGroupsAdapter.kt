package com.project.collabexpense.presentation.ui.fragment.groups.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.project.collabexpense.R
import com.project.collabexpense.base.BaseAdapter
import com.project.collabexpense.data.remote.models.GroupData
import com.project.collabexpense.databinding.ItemMygroupsBinding

class MyGroupsAdapter (
    private val onClickListener: (model: GroupData) -> Unit,
) : BaseAdapter<GroupData>(

    diffCallback = object : DiffUtil.ItemCallback<GroupData>() {

        override fun areItemsTheSame(oldItem: GroupData, newItem: GroupData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GroupData, newItem: GroupData): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_mygroups,
            parent,
            false
        )
    }

    override fun bind(binding: ViewDataBinding, item: GroupData, position: Int) {
        when (binding) {
            is ItemMygroupsBinding ->
                binding.apply {
                    categoryTv.text = item.name

                    card.setOnClickListener {
                        onClickListener.invoke(item)
                    }
                }
        }
    }

}