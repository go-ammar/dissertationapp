package com.project.collabexpense.data.remote.models

data class MonthlyCategorySpend(
    val category: String,
    val month: Int,
    val year: Int,
    val totalAmount: Long,
    val budgetAmount: Int
)
