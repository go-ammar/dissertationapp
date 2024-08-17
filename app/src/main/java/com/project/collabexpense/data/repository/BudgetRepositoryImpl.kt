package com.project.collabexpense.data.repository

import com.project.collabexpense.data.local.dao.MyDao
import com.project.collabexpense.data.remote.ApiService
import com.project.collabexpense.data.remote.models.Budget
import com.project.collabexpense.data.remote.models.MonthlyCategorySpend
import com.project.collabexpense.domain.repository.BudgetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody
import retrofit2.http.Body
import javax.inject.Inject

class BudgetRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : BudgetRepository {

    override fun getBudgets(): Flow<List<Budget>> {
        return flow {
            emit(apiService.getBudgets())
        }.flowOn(Dispatchers.IO)

    }

    override fun getBudgetsWithData(): Flow<List<MonthlyCategorySpend>> {

        return flow {
            emit(apiService.getBudgetsWithAllData())
        }.flowOn(Dispatchers.IO)

    }

    override fun addBudgets(requestBody: RequestBody): Flow<Budget> {
        return flow {
            emit(apiService.addBudget(requestBody))
        }.flowOn(Dispatchers.IO)
    }
}