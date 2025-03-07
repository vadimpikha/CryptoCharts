package com.vadimpikha.domain.network

import com.vadimpikha.domain.network.model.CoinInfo

interface CryptoInfoRepository {

    suspend fun getCoinsInfo(
        currency: String = "usd",
        order: String = "market_cap_desc",
        perPage: Int = 100,
        page: Int = 1,
        sparkline: Boolean = false
    ): List<CoinInfo>

}