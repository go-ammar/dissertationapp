package com.project.collabexpense.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mydataentity")
data class MyDataEntity (
    @PrimaryKey
    val id: String,
    val name: String
)
