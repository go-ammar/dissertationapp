package com.project.collabexpense.domain.repository

import com.project.collabexpense.data.remote.models.AuthResponse
import com.project.collabexpense.data.remote.models.UserInfo
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface AuthRepository {

    fun login(requestBody: RequestBody) : Flow<AuthResponse>

    fun signUp(requestBody: RequestBody) : Flow<UserInfo>

}