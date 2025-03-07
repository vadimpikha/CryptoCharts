package com.vadimpikha.di

import com.vadimpikha.data.network.AppHttpClient
import com.vadimpikha.data.network.CoinGeckoDataSource
import com.vadimpikha.data.network.CryptoInfoRepositoryImpl
import com.vadimpikha.domain.network.CryptoInfoRepository
import com.vadimpikha.domain.usecase.GetCryptoCoinsInfoFlowUseCase
import com.vadimpikha.presentation.list.CryptoCoinsListViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private val AppModule = module {
    single { AppHttpClient }
    factory { CoinGeckoDataSource(get()) }
    single<CryptoInfoRepository> { CryptoInfoRepositoryImpl(get()) }
    factory { GetCryptoCoinsInfoFlowUseCase(get()) }
    viewModel { CryptoCoinsListViewModel(get()) }
}

fun initKoin() {
    startKoin {
        modules(AppModule)
    }
}