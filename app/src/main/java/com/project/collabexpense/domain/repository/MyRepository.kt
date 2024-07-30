package com.project.collabexpense.domain.repository

import com.project.collabexpense.data.remote.dto.TestLocal
import kotlinx.coroutines.flow.Flow
import com.project.collabexpense.domain.model.MyData

interface MyRepository {

    fun getData(): Flow<List<MyData>>
    suspend fun refreshData()

    fun getLocal(): Flow<List<TestLocal>>
}