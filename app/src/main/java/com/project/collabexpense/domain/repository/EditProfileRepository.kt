package com.project.collabexpense.domain.repository

import com.project.collabexpense.data.remote.models.UserInfo
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface EditProfileRepository {

    fun getDetails() : Flow<UserInfo>
    fun updateDetails(body: RequestBody) : Flow<UserInfo>

}