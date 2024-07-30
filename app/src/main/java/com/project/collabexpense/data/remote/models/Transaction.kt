package com.project.collabexpense.data.remote.models

data class Transaction(
    val id: Long,
    var category: String,
    val transactionDate: Long,
    var amount: Int,
    val userId: String,
)
