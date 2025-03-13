package com.vadimpikha.presentation.coil

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import com.vadimpikha.utils.getAppFilesDirectory

fun createImageLoader(): ImageLoader {
    val context = PlatformContext.INSTANCE

    return ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, 0.25)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(getAppFilesDirectory().resolve("image_cache"))
                .maxSizePercent(0.02)
                .build()
        }
        .build()
}