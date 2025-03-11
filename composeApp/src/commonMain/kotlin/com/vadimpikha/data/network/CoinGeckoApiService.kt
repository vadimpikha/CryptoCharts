package com.vadimpikha.data.network

import com.vadimpikha.data.network.model.CoinInfoApiModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

internal class CoinGeckoApiService(private val client: HttpClient) {

    suspend fun getCoinsInfo(
        currency: String,
    ): List<CoinInfoApiModel> = client.get(Urls.COINS_MARKETS) {
        parameter("vs_currency", currency)
        parameter("precision", 2)
        parameter("per_page", 100)
        parameter("page", 1)
    }.body()

    private object Urls {
        private const val BASE_URL = "https://api.coingecko.com/api/v3/"
        const val COINS_MARKETS = "${BASE_URL}coins/markets"
    }
}