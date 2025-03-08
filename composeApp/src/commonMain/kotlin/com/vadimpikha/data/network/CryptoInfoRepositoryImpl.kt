package com.vadimpikha.data.network

import com.vadimpikha.data.db.AppDatabase
import com.vadimpikha.data.db.model.toDomainEntity
import com.vadimpikha.data.network.model.toEntityModel
import com.vadimpikha.domain.network.CryptoInfoRepository
import com.vadimpikha.domain.network.model.CoinInfo
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

internal class CryptoInfoRepositoryImpl(
    private val coinGeckoApiService: CoinGeckoApiService,
    private val appDatabase: AppDatabase
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
                    order = "market_cap_desc",
                    perPage = 100,
                    page = 1,
                    sparkline = false
                )
                appDatabase.coinsDao().insertCoinsInfo(fetchedCoinsInfo.map { it.toEntityModel() })

            } catch (e: Exception) {
                if (e is CancellationException || cachedCoinsInfo.isEmpty()) throw e
            }
        }

        emitAll(
            appDatabase.coinsDao().getCoinsInfoFlow()
                .map { list -> list.map { it.toDomainEntity() } }
        )
    }

}