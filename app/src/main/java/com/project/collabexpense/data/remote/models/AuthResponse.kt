package com.project.collabexpense.data.remote.models

import com.google.gson.annotations.SerializedName

data class AuthResponse(

    @SerializedName("accessToken") var accessToken: String? = null,
    @SerializedName("refreshToken") var refreshToken: String? = null,
    @SerializedName("userId") var userId: String? = null

)