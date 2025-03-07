package com.vadimpikha.data.network

import com.vadimpikha.data.network.model.toDomain
import com.vadimpikha.domain.network.CryptoInfoRepository
import com.vadimpikha.domain.network.model.CoinInfo

internal class CryptoInfoRepositoryImpl(
    private val coinGeckoDataSource: CoinGeckoDataSource
): CryptoInfoRepository {

    override suspend fun getCoinsInfo(
        currency: String,
        order: String,
        perPage: Int,
        page: Int,
        sparkline: Boolean
    ): List<CoinInfo> {
        return coinGeckoDataSource.getCoinsInfo(currency, order, perPage, page, sparkline)
            .map { it.toDomain() }
    }

}