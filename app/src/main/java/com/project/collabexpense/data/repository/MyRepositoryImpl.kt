package com.project.collabexpense.data.repository

import com.project.collabexpense.data.local.dao.MyDao
import com.project.collabexpense.data.remote.ApiService
import com.project.collabexpense.data.remote.dto.TestLocal
import com.project.collabexpense.data.repository.mappers.toDomain
import com.project.collabexpense.data.repository.mappers.toEntity
import com.project.collabexpense.domain.model.MyData
import com.project.collabexpense.domain.repository.MyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val myDao: MyDao
) : MyRepository {

    override fun getData(): Flow<List<MyData>> {
        return myDao.getAllData().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun refreshData() {
        val data = apiService.getData()
        myDao.insertAll(data.map { it.toEntity() })
    }

    override fun getLocal(): Flow<List<TestLocal>> {
        return flow {
            emit(
                apiService.testLocal(
                )
            )
        }.flowOn(Dispatchers.IO)
    }


}