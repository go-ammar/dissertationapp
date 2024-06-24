package com.project.collabexpense.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.collabexpense.data.local.entities.MyDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {

    @Query("SELECT * FROM mydataentity")
    fun getAllData(): Flow<List<MyDataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<MyDataEntity>)
}