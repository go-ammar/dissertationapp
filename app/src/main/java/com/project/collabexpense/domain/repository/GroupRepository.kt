package com.project.collabexpense.domain.repository

import com.project.collabexpense.data.remote.models.GroupData
import com.project.collabexpense.data.remote.models.GroupDetails
import com.project.collabexpense.data.remote.models.GroupTransactionAdded
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface GroupRepository {

    fun getUserGroups() : Flow<List<GroupData>>

    fun createUserGroup(requestBody: RequestBody) : Flow<GroupData>

    fun getGroupDetails(groupId: Long): Flow<GroupDetails>

    fun addGroupTransaction(requestBody: RequestBody): Flow<GroupTransactionAdded>

}