package com.project.collabexpense.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.collabexpense.data.local.dao.MyDao
import com.project.collabexpense.data.local.entities.MyDataEntity

@Database(entities = [MyDataEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun myDao(): MyDao

}