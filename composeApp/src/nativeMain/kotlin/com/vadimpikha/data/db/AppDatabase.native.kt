package com.vadimpikha.data.db

import androidx.room.Room
import androidx.room.RoomDatabase
import com.vadimpikha.util.getDocumentDirectoryPath
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = getDocumentDirectoryPath() + "/$DATABASE_NAME"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
    )
}

