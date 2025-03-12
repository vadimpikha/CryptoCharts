package com.vadimpikha.domain.usecase

import com.vadimpikha.domain.network.CryptoInfoRepository
import com.vadimpikha.domain.network.model.CoinInfo
import com.vadimpikha.domain.network.model.CoinsSorting
import com.vadimpikha.domain.prefs.PrefsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.until

class GetCoinsInfoFlowUseCase(
    private val cryptoInfoRepository: CryptoInfoRepository,
    private val prefsManager: PrefsManager,
    private val clock: Clock
) {

    operator fun invoke(
        sorting: CoinsSorting = CoinsSorting.Default
    ): Flow<List<CoinInfo>> = flow {
        val innerFlow = cryptoInfoRepository.getCoinsInfoFlow(forceFetch = shouldForceSync())
            .map { it.sortedWith(sorting.getComparator()) }
        emitAll(innerFlow)
    }


    private suspend fun shouldForceSync(): Boolean {
        val syncInfo = prefsManager.getCoinsSyncInfo() ?: return true
        val minutesSinceSync = syncInfo.timestamp.until(clock.now(), DateTimeUnit.MINUTE)
        return minutesSinceSync >= COINS_SYNC_PERIOD_MINUTES
    }

    companion object {
        private const val COINS_SYNC_PERIOD_MINUTES = 15
    }
}