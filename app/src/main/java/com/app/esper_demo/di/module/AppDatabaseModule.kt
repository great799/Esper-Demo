package com.app.esper_demo.di.module

import androidx.room.Room
import com.app.esper_demo.EsperApplication
import com.app.esper_demo.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module which provides all required dependencies about database
 */
@Module
class AppDatabaseModule(private val application: EsperApplication) {
    /**
     * Provides the database object.
     * @return the database object
     */
    @Singleton
    @Provides
    internal fun provideDatabaseObject(): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java, "esper-db"
        ).build()
    }
}