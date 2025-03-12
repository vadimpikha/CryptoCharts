package com.vadimpikha.data.network

import com.vadimpikha.data.db.AppDatabase
import com.vadimpikha.data.db.model.toDomainEntity
import com.vadimpikha.data.network.model.toEntityModel
import com.vadimpikha.domain.network.CryptoInfoRepository
import com.vadimpikha.domain.network.model.CoinInfo
import com.vadimpikha.domain.prefs.PrefsManager
import com.vadimpikha.domain.prefs.model.CoinsSyncInfo
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

internal class CryptoInfoRepositoryImpl(
    private val coinGeckoApiService: CoinGeckoApiService,
    private val appDatabase: AppDatabase,
    private val prefsManager: PrefsManager
) : CryptoInfoRepository {

    override fun getCoinsInfoFlow(forceFetch: Boolean): Flow<List<CoinInfo>> = flow {
        val cachedCoinsInfo = appDatabase.coinsDao().getCoinsInfoFlow().first()
        if (cachedCoinsInfo.isNotEmpty()) {
            emit(cachedCoinsInfo.map { it.toDomainEntity() })
        }

        if (forceFetch || cachedCoinsInfo.isEmpty()) {
            try {
                val fetchedCoinsInfo = coinGeckoApiService.getCoinsInfo(
                    currency = "usd",
                )
                appDatabase.coinsDao().run {
                    clearCoinsInfo()
                    insertCoinsInfo(fetchedCoinsInfo.map { it.toEntityModel() })
                }
                prefsManager.updateCoinsSyncInfo(
                    CoinsSyncInfo(timestamp = Clock.System.now())
                )
            } catch (e: Exception) {
                if (e is CancellationException || cachedCoinsInfo.isEmpty()) throw e
            }
        }

        emitAll(
            appDatabase.coinsDao().getCoinsInfoFlow()
                .map { list -> list.map { it.toDomainEntity() } }
        )
    }

    override suspend fun getCoinInfo(id: String): CoinInfo =
        appDatabase.coinsDao().getCoinInfo(id).toDomainEntity()
}