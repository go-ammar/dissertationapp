package com.project.collabexpense.data.repository

import com.project.collabexpense.data.remote.ApiService
import com.project.collabexpense.data.remote.models.Transaction
import com.project.collabexpense.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TransactionRepository {

    override fun getCategories(): Flow<List<String>> {
        return flow {
            emit(apiService.getUserBudgetCategories())
        }.flowOn(Dispatchers.IO)
    }

    override fun getTransactions(): Flow<List<Transaction>> {
        return flow {
            emit(apiService.getUserTransactionHistory())
        }.flowOn(Dispatchers.IO)
    }

    override fun createTransactions(body: RequestBody): Flow<Transaction> {
        return flow {
            emit(apiService.addTransaction(body = body))
        }
    }

//    override fun createTransactions(): Flow<String> {
//        return flow {
//            emit(apiService.getUserBudgetCategories())
//        }.flowOn(Dispatchers.IO)
//    }


}