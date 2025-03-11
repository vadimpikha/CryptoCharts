package com.vadimpikha.data.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import net.harawata.appdirs.AppDirsFactory
import java.io.File

fun createDataStore(): DataStore<Preferences> = createDataStoreAtPath(
    producePath = {
        val appDirs = AppDirsFactory.getInstance()
        val userDataDir = appDirs.getUserDataDir("CryptoCharts", null, null)
        File(userDataDir, dataStoreFileName).absolutePath
    }
)