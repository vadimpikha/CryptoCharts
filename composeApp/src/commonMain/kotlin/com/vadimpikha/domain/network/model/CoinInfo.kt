package com.vadimpikha.domain.network.model

data class CoinInfo(
    val id: String,
    val symbol: String,
    val name: String,
    val iconUrl: String,
    val currentPrice: Double,
    val priceChange: Double,
    val marketCapRank: Int,
)