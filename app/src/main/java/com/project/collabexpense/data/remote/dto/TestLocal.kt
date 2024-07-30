package com.project.collabexpense.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TestLocal(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("isReminderSet") var isReminderSet: Boolean? = null,
    @SerializedName("isTodoOpen") var isTodoOpen: Boolean? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("priority") var priority: String? = null
)
