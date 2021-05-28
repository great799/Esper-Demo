package com.app.esper_demo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.esper_demo.network.model.ExclusionDetail

@Dao
interface ExclusionDao {
    @Query("SELECT * FROM ExclusionDetail")
    suspend fun getAll(): List<ExclusionDetail>

    @Query("DELETE FROM ExclusionDetail")
    suspend fun nukeTable()

    @Insert
    suspend fun insertExclusions(exclusions: List<ExclusionDetail>)
}