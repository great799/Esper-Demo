package com.app.esper_demo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.esper_demo.database.converters.ExclusionItemConverter
import com.app.esper_demo.database.converters.OptionDetailConverter
import com.app.esper_demo.database.dao.ExclusionDao
import com.app.esper_demo.database.dao.MobileFeatureDao
import com.app.esper_demo.network.model.ExclusionDetail
import com.app.esper_demo.network.model.MobileFeatureDetail

@Database(
    entities = [MobileFeatureDetail::class, ExclusionDetail::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(ExclusionItemConverter::class, OptionDetailConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun featureDao(): MobileFeatureDao
    abstract fun exclusionDao(): ExclusionDao
}