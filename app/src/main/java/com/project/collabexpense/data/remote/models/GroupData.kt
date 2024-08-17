package com.project.collabexpense.data.remote.models

import com.google.gson.annotations.SerializedName

data class GroupData(
    @SerializedName("id"        ) var id        : Int?    = null,
    @SerializedName("name"      ) var name      : String? = null,
    @SerializedName("createdBy" ) var createdBy : String? = null
)
