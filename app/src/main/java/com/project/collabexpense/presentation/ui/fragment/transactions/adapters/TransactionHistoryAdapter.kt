package com.project.collabexpense.presentation.ui.fragment.transactions.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.project.collabexpense.R
import com.project.collabexpense.base.BaseAdapter
import com.project.collabexpense.data.remote.models.Transaction
import com.project.collabexpense.databinding.ItemTransactionHistoryBinding
import com.project.collabexpense.domain.model.MyData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionHistoryAdapter (
    private val onClickListener: (model: MyData) -> Unit,
) : BaseAdapter<Transaction>(

    diffCallback = object : DiffUtil.ItemCallback<Transaction>() {

        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_transaction_history,
            parent,
            false
        )
    }

    override fun bind(binding: ViewDataBinding, item: Transaction, position: Int) {
        when (binding) {
            is ItemTransactionHistoryBinding ->
                binding.apply {
                    categoryTv.text = item.category
                    spentTv.text = item.amount.toString()
                    val date = Date(item.transactionDate)
                    val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    dateTv.text = "Date: " + format.format(date)
                }
        }
    }

}