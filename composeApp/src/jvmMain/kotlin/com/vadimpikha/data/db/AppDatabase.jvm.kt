package com.vadimpikha.data.db

import androidx.room.Room
import androidx.room.RoomDatabase
import net.harawata.appdirs.AppDirs
import net.harawata.appdirs.AppDirsFactory
import java.io.File

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val appDirs = AppDirsFactory.getInstance()
    val userDataDir = appDirs.getUserDataDir("CryptoCharts", null, null)
    val dbFile = File(userDataDir, DATABASE_NAME)
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    )
}