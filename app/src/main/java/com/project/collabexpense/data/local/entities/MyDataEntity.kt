package com.project.collabexpense.data.local.entities

import androidx.room.Entity

@Entity(tableName = "mydataentity")
data class MyDataEntity (
    val id: String,
    val name: String
)
