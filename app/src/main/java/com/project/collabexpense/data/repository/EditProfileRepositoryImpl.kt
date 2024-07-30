package com.project.collabexpense.data.repository

import com.project.collabexpense.data.remote.ApiService
import com.project.collabexpense.data.remote.models.UserInfo
import com.project.collabexpense.domain.repository.EditProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody
import javax.inject.Inject

class EditProfileRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : EditProfileRepository {
    override fun getDetails(): Flow<UserInfo> {
        return flow {
            emit(apiService.getUserDetails())
        }.flowOn(Dispatchers.IO)
    }

    override fun updateDetails(body: RequestBody): Flow<UserInfo> {
        return flow {
            emit (apiService.updateUser(body))
        }.flowOn(Dispatchers.IO)
    }

}