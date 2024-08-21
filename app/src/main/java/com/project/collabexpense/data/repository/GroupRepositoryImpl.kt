package com.project.collabexpense.data.repository

import com.project.collabexpense.data.remote.ApiService
import com.project.collabexpense.data.remote.models.GroupData
import com.project.collabexpense.data.remote.models.GroupDetails
import com.project.collabexpense.data.remote.models.GroupTransactionAdded
import com.project.collabexpense.domain.repository.GroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody

class GroupRepositoryImpl(
    private val apiService: ApiService
) : GroupRepository {

    override fun getUserGroups(): Flow<List<GroupData>> {
        return flow {
            emit(apiService.getUserGroups())
        }.flowOn(Dispatchers.IO)
    }

    override fun createUserGroup(requestBody: RequestBody): Flow<GroupData> {
        return flow {
            emit(apiService.createGroup(requestBody))
        }.flowOn(Dispatchers.IO)
    }

    override fun getGroupDetails(groupId: Long): Flow<GroupDetails> {
        return flow {
            emit(apiService.getGroupDetails(groupId))
        }.flowOn(Dispatchers.IO)
    }

    override fun addGroupTransaction(requestBody: RequestBody): Flow<GroupTransactionAdded> {
        return flow {
            emit(apiService.addGroupTransaction(requestBody))
        }.flowOn(Dispatchers.IO)
    }

}