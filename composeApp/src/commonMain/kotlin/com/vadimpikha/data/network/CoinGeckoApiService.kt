package com.vadimpikha.data.network

import com.vadimpikha.data.network.model.CoinInfoApiModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

internal class CoinGeckoApiService(private val client: HttpClient) {

    suspend fun getCoinsInfo(
        currency: String,
        order: String,
        perPage: Int,
        page: Int,
        sparkline: Boolean
    ): List<CoinInfoApiModel> = client.get(Urls.COINS_MARKETS) {
        parameter("vs_currency", currency)
        parameter("order", order)
        parameter("per_page", perPage)
        parameter("page", page)
        parameter("sparkline", sparkline)
    }.body()

    private object Urls {
        private const val BASE_URL = "https://api.coingecko.com/api/v3/"
        const val COINS_MARKETS = "${BASE_URL}coins/markets"
    }
}