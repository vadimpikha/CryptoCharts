package com.vadimpikha.data.network.model

import com.vadimpikha.data.db.model.CoinInfoEntity
import com.vadimpikha.domain.network.model.CoinInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CoinInfoApiModel(
    @SerialName("id") val id: String,
    @SerialName("symbol") val symbol: String,
    @SerialName("name") val name: String,
    @SerialName("image") val iconUrl: String,
    @SerialName("current_price") val currentPrice: Double,
    @SerialName("price_change_percentage_24h") val priceChange: Double
)

internal fun CoinInfoApiModel.toEntityModel(): CoinInfoEntity {
    return CoinInfoEntity(
        id = id,
        symbol = symbol,
        name = name,
        iconUrl = iconUrl,
        currentPrice = currentPrice,
        priceChange = priceChange
    )
}