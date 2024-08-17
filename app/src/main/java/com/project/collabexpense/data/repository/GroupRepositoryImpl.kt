package com.project.collabexpense.data.repository

import com.project.collabexpense.data.remote.ApiService
import com.project.collabexpense.data.remote.models.GroupData
import com.project.collabexpense.domain.repository.GroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GroupRepositoryImpl(
    private val apiService: ApiService
) : GroupRepository {

    override fun getUserGroups(): Flow<List<GroupData>> {
        return flow {
            emit(apiService.getUserGroups())
        }.flowOn(Dispatchers.IO)
    }

}