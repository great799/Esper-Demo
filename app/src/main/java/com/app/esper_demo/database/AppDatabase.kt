package com.app.esper_demo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.esper_demo.network.model.ExclusionDetail
import com.app.esper_demo.network.model.MobileFeatureDetail

@Database(
    entities = [MobileFeatureDetail::class, ExclusionDetail::class],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun featureDao(): MobileFeatureDao
    abstract fun exclusionDao(): ExclusionDao
}