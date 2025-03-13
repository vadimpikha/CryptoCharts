package com.vadimpikha.domain.network.model

import androidx.compose.runtime.Stable

@Stable
sealed class CoinsSorting(
    private val selector: (CoinInfo) -> Comparable<*>
) {
    abstract val ascending: Boolean
    abstract val orderBy: OrderBy

    data class ByMarketCapRank(
        override val ascending: Boolean
    ) : CoinsSorting(
        selector = { it.marketCapRank * -1 } //invert order for more natural order (smaller number of rank - better)
    ) {
        override val orderBy: OrderBy = OrderBy.Rank
    }

    data class By24HoursChange(
        override val ascending: Boolean
    ) : CoinsSorting(CoinInfo::priceChange) {
        override val orderBy: OrderBy = OrderBy.PriceChange
    }

    data class ByPrice(
        override val ascending: Boolean
    ) : CoinsSorting(CoinInfo::currentPrice) {
        override val orderBy: OrderBy = OrderBy.CurrentPrice
    }

    data class ByMarketCap(
        override val ascending: Boolean
    ) : CoinsSorting(CoinInfo::marketCap) {
        override val orderBy: OrderBy = OrderBy.MarketCap
    }

    fun getComparator() = if (ascending) compareBy(selector) else compareByDescending(selector)

    fun copyWith(ascending: Boolean): CoinsSorting {
        return when (this) {
            is By24HoursChange -> copy(ascending = ascending)
            is ByMarketCap -> copy(ascending = ascending)
            is ByMarketCapRank -> copy(ascending = ascending)
            is ByPrice -> copy(ascending = ascending)
        }
    }

    companion object {
        val Default: CoinsSorting = ByMarketCapRank(ascending = false)

        @Suppress("FunctionName")
        fun By(orderBy: OrderBy, ascending: Boolean = false): CoinsSorting {
            return when (orderBy) {
                OrderBy.Rank -> ByMarketCapRank(ascending)
                OrderBy.CurrentPrice -> ByPrice(ascending)
                OrderBy.PriceChange -> By24HoursChange(ascending)
                OrderBy.MarketCap -> ByMarketCap(ascending)
            }
        }
    }
}

enum class OrderBy {
    Rank,
    CurrentPrice,
    PriceChange,
    MarketCap,
}