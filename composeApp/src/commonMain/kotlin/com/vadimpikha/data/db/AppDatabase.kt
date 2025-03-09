package com.vadimpikha.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.vadimpikha.data.db.model.CoinInfoEntity

@Database(entities = [CoinInfoEntity::class], version = 2)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinsDao(): CoinsDao


    companion object {
        fun create(builder: Builder<AppDatabase>): AppDatabase {
            return builder
                .setDriver(BundledSQLiteDriver())
                .fallbackToDestructiveMigration(true) //TODO::remove it after initial release
                .build()
        }
    }
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

internal const val DATABASE_NAME = "app_database.db"