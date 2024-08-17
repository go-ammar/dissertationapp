package com.project.collabexpense.domain.repository

import com.project.collabexpense.data.remote.models.GroupData
import kotlinx.coroutines.flow.Flow

interface GroupRepository {

    fun getUserGroups() : Flow<List<GroupData>>

}