package com.vadimpikha.utils

import net.harawata.appdirs.AppDirsFactory
import java.io.File

fun getAppFilesDirectory(): File {
    val appDirs = AppDirsFactory.getInstance()
    val userDataDir = appDirs.getUserDataDir("CryptoCharts", null, null)
    return File(userDataDir)
}