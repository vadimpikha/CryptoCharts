package com.vadimpikha.data.db

import androidx.room.Room
import androidx.room.RoomDatabase
import com.vadimpikha.utils.getAppFilesDirectory

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    return Room.databaseBuilder<AppDatabase>(
        name = getAppFilesDirectory().resolve(DATABASE_NAME).absolutePath,
    )
}