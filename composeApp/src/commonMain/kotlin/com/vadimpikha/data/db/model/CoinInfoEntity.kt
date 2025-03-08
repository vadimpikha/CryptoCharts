package com.vadimpikha.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vadimpikha.domain.network.model.CoinInfo

@Entity(tableName = "coins_info")
data class CoinInfoEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "symbol")
    val symbol: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "icon_url")
    val iconUrl: String,
    @ColumnInfo(name = "current_price")
    val currentPrice: Double,
    @ColumnInfo(name = "price_change")
    val priceChange: Double
)

fun CoinInfo.toEntityModel(): CoinInfoEntity {
    return CoinInfoEntity(
        id = id,
        symbol = symbol,
        name = name,
        iconUrl = iconUrl,
        currentPrice = currentPrice,
        priceChange = priceChange
    )
}

fun CoinInfoEntity.toDomainEntity(): CoinInfo {
    return CoinInfo(
        id = id,
        symbol = symbol,
        name = name,
        iconUrl = iconUrl,
        currentPrice = currentPrice,
        priceChange = priceChange
    )
}