package com.vadimpikha.data.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.vadimpikha.util.getDocumentDirectoryPath

fun createDataStore(): DataStore<Preferences> = createDataStoreAtPath(
    producePath = {
        getDocumentDirectoryPath() + "/$dataStoreFileName"
    }
)