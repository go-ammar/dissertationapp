package com.project.collabexpense.data.repository

import com.project.collabexpense.data.remote.ApiService
import com.project.collabexpense.data.remote.models.AuthResponse
import com.project.collabexpense.data.remote.models.UserInfo
import com.project.collabexpense.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {
    override fun login(requestBody: RequestBody): Flow<AuthResponse> {
        return flow {
            emit(apiService.login(requestBody))
        }.flowOn(Dispatchers.IO)
    }

    override fun signUp(requestBody: RequestBody): Flow<UserInfo> {
        return flow {
            emit(apiService.signUp(requestBody))
        }.flowOn(Dispatchers.IO)
    }


}