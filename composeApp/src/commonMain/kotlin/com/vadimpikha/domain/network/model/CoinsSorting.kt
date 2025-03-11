package com.vadimpikha.domain.network.model

sealed class CoinsSorting(
    private val selector: (CoinInfo) -> Comparable<*>
) {
    protected abstract val descending: Boolean

    data class ByMarketCapRank(
        override val descending: Boolean
    ) : CoinsSorting(CoinInfo::marketCapRank)

    data class By24HoursChange(
        override val descending: Boolean
    ) : CoinsSorting(CoinInfo::priceChange)

    data class ByPrice(
        override val descending: Boolean
    ) : CoinsSorting(CoinInfo::currentPrice)

    fun getComparator() = if (descending) compareByDescending(selector) else compareBy(selector)
}