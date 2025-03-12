package com.vadimpikha.domain

import com.vadimpikha.domain.model.waitData
import com.vadimpikha.domain.network.CryptoInfoRepository
import com.vadimpikha.domain.network.model.CoinInfo
import com.vadimpikha.domain.network.model.CoinsSorting
import com.vadimpikha.domain.prefs.PrefsManager
import com.vadimpikha.domain.prefs.model.CoinsSyncInfo
import com.vadimpikha.domain.usecase.GetCoinsInfoFlowUseCase
import dev.mokkery.answering.returns
import dev.mokkery.answering.sequentiallyReturns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verify.VerifyMode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.time.Duration.Companion.minutes

class GetCoinsInfoUseCaseTest {

    private val testCoinsInfo = listOf(
        CoinInfo("btc", "BTC", "Bitcoin", "", 80000.0, 5.0, 1),
        CoinInfo("eth", "ETH", "Ethereum", "", 2000.0, 12.0, 2),
        CoinInfo("sol", "SOL", "Solana", "", 200.0, -1.0, 3),
    )

    private val testCurrentTime = Instant.fromEpochMilliseconds(1_000_000)

    private val testCoinsSyncInfo = listOf(
        CoinsSyncInfo(testCurrentTime.minus(5.minutes)),
        null,
        CoinsSyncInfo(testCurrentTime.minus(20.minutes)),
    )

    private val clock = object : Clock {
        override fun now(): Instant = testCurrentTime
    }

    private val repository = mock<CryptoInfoRepository> {
        every { getCoinsInfoFlow(any()) } returns flowOf(testCoinsInfo)
    }

    private val prefsManager = mock<PrefsManager> {
        everySuspend { getCoinsSyncInfo() } sequentiallyReturns (testCoinsSyncInfo)
    }

    private val useCase = GetCoinsInfoFlowUseCase(repository, prefsManager, clock)

    @Test
    fun testForceRefreshFlag() = runTest {
        useCase().first() //should be called with 5 minutes cache, so flag should be false
        useCase().first() //should be called with null syncInfo, so flag should be true
        useCase().first() //should be called with 20 minutes cache, so flag should be true

        verify(mode = VerifyMode.order) {
            repository.getCoinsInfoFlow(forceFetch = false)
            repository.getCoinsInfoFlow(forceFetch = true)
            repository.getCoinsInfoFlow(forceFetch = true)
        }
    }

    @Test
    fun testCoinsOrder() = runTest {
        val sortingVariants = listOf(
            CoinsSorting.ByMarketCapRank(descending = false),
            CoinsSorting.By24HoursChange(descending = true),
            CoinsSorting.ByPrice(descending = false),
        )

        val coins1 = useCase(sortingVariants[0]).first()
        val coins2 = useCase(sortingVariants[1]).first()
        val coins3 = useCase(sortingVariants[2]).first()
        val expectedRanksSortingIds = listOf("btc", "eth", "sol")
        val expectedPriceChangeSortingIds = listOf("eth", "btc", "sol")
        val expectedPriceSortingIds = listOf("sol", "eth", "btc")


        assertContentEquals(expectedRanksSortingIds, coins1.map { it.id })
        assertContentEquals(expectedPriceChangeSortingIds, coins2.map { it.id })
        assertContentEquals(expectedPriceSortingIds, coins3.map { it.id })
    }
}