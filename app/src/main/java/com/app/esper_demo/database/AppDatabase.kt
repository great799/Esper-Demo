package com.app.esper_demo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.esper_demo.network.model.MobileFeatureDetail

@Database(entities = [MobileFeatureDetail::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockDao(): MobileFeatureDao
}