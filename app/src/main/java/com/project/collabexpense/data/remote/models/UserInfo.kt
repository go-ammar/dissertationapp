package com.project.collabexpense.data.remote.models

data class UserInfo(
    val id: Long,
    val email: String,
    val name: String,
    val dob: Long,
    var updatedToken : String = ""
)
