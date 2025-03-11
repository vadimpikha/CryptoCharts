package com.vadimpikha.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.Preferences

fun createDataStore(context: Context): DataStore<Preferences> = createDataStoreAtPath(
    producePath = { context.dataStoreFile(dataStoreFileName).absolutePath }
)