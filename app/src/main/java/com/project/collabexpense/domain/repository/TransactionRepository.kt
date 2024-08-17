package com.project.collabexpense.domain.repository

import com.project.collabexpense.data.remote.models.Transaction
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface TransactionRepository {

    fun getCategories(): Flow<List<String>>


    fun getTransactions() : Flow<List<Transaction>>

    fun createTransactions(body: RequestBody) : Flow<Transaction>

}