package com.project.collabexpense.domain.repository

import com.project.collabexpense.data.remote.models.Budget
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import retrofit2.http.Body

interface BudgetRepository {

    fun getBudgets(): Flow<List<Budget>>

    fun addBudgets(requestBody: RequestBody): Flow<Budget>

}