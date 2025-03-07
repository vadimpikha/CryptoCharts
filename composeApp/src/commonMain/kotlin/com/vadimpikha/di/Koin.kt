package com.vadimpikha.di

import com.vadimpikha.data.db.AppDatabase
import com.vadimpikha.data.network.CoinGeckoApiService
import com.vadimpikha.data.network.CryptoInfoRepositoryImpl
import com.vadimpikha.data.network.createHttpClient
import com.vadimpikha.domain.network.CryptoInfoRepository
import com.vadimpikha.domain.usecase.GetCryptoCoinsInfoFlowUseCase
import com.vadimpikha.domain.utils.DefaultDispatcherProvider
import com.vadimpikha.domain.utils.DispatcherProvider
import com.vadimpikha.presentation.list.CoinsListViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

private val CommonModule = module {
    single { DefaultDispatcherProvider }.bind<DispatcherProvider>()
    singleOf (::createHttpClient)
    factoryOf(::CoinGeckoApiService)
    singleOf(::CryptoInfoRepositoryImpl).bind<CryptoInfoRepository>()
    factoryOf(::GetCryptoCoinsInfoFlowUseCase)
    viewModelOf (::CoinsListViewModel)
    singleOf(AppDatabase::create)
}

expect val PlatformModule: Module

fun initKoin(extraDeclaration: KoinAppDeclaration? = null) {
    startKoin {
        modules(CommonModule, PlatformModule)
        extraDeclaration?.invoke(this)
    }
}