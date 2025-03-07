package com.vadimpikha.di

import com.vadimpikha.data.network.AppHttpClient
import com.vadimpikha.data.network.CoinGeckoDataSource
import com.vadimpikha.data.network.CryptoInfoRepositoryImpl
import com.vadimpikha.domain.network.CryptoInfoRepository
import com.vadimpikha.domain.usecase.GetCryptoCoinsInfoFlowUseCase
import com.vadimpikha.domain.utils.DefaultDispatcherProvider
import com.vadimpikha.domain.utils.DispatcherProvider
import com.vadimpikha.presentation.list.CoinsListViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private val AppModule = module {
    factory<DispatcherProvider> { DefaultDispatcherProvider }
    single { AppHttpClient }
    factory { CoinGeckoDataSource(get()) }
    single<CryptoInfoRepository> { CryptoInfoRepositoryImpl(get()) }
    factory { GetCryptoCoinsInfoFlowUseCase(get()) }
    viewModel { CoinsListViewModel(get()) }
}

fun initKoin(extraDeclaration: KoinApplication.() -> Unit = {}) {
    startKoin {
        modules(AppModule)
        extraDeclaration()
    }
}