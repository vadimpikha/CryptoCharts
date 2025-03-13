package com.vadimpikha.data.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.vadimpikha.utils.getAppFilesDirectory

fun createDataStore(): DataStore<Preferences> = createDataStoreAtPath(
    producePath = {
        getAppFilesDirectory().resolve(dataStoreFileName).absolutePath
    }
)