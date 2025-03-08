package com.vadimpikha.di

import com.vadimpikha.data.db.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

actual val PlatformModule: Module = module {
    factoryOf(::getDatabaseBuilder)
}