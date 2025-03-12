package com.vadimpikha.di

import com.vadimpikha.data.db.AppDatabase
import com.vadimpikha.data.network.CoinGeckoApiService
import com.vadimpikha.data.prefs.PrefsManagerImpl
import com.vadimpikha.data.network.CryptoInfoRepositoryImpl
import com.vadimpikha.data.network.createHttpClient
import com.vadimpikha.domain.network.CryptoInfoRepository
import com.vadimpikha.domain.prefs.PrefsManager
import com.vadimpikha.domain.usecase.GetCoinsInfoFlowUseCase
import com.vadimpikha.domain.usecase.GetCoinInfoUseCase
import com.vadimpikha.domain.utils.DefaultDispatcherProvider
import com.vadimpikha.domain.utils.DispatcherProvider
import com.vadimpikha.presentation.details.CoinDetailsViewModel
import com.vadimpikha.presentation.list.CoinsListViewModel
import kotlinx.datetime.Clock
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
    factoryOf(::GetCoinsInfoFlowUseCase)
    factoryOf(::GetCoinInfoUseCase)
    viewModelOf (::CoinsListViewModel)
    viewModelOf(::CoinDetailsViewModel)
    singleOf(AppDatabase::create)
    singleOf(::PrefsManagerImpl).bind<PrefsManager>()
    single { Clock.System }.bind<Clock>()
}

expect val PlatformModule: Module

fun initKoin(extraDeclaration: KoinAppDeclaration? = null) {
    startKoin {
        modules(CommonModule, PlatformModule)
        extraDeclaration?.invoke(this)
    }
}