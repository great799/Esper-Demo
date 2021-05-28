package com.app.esper_demo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.esper_demo.network.model.MobileFeatureDetail

@Dao
interface MobileFeatureDao {
    @Query("SELECT * FROM mobilefeaturedetail")
    suspend fun getAll(): List<MobileFeatureDetail>

    @Query("DELETE FROM mobilefeaturedetail")
    suspend fun nukeTable()

    @Insert
    suspend fun insertListOfStocks(features: List<MobileFeatureDetail>)
}