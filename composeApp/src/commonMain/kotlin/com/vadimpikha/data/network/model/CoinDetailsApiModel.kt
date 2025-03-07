package com.vadimpikha.data.network.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class CoinDetailsApiModel(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: Description,
    @SerialName("market_data") val marketData: MarketData
) {
    @Serializable
    data class Description(@SerialName("en") val en: String)

    @Serializable
    data class MarketData(@SerialName("current_price") val currentPrice: Map<String, Double>)
}