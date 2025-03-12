package com.vadimpikha.domain.network

import com.vadimpikha.domain.network.model.CoinInfo
import kotlinx.coroutines.flow.Flow

interface CryptoInfoRepository {

    fun getCoinsInfoFlow(forceFetch: Boolean): Flow<List<CoinInfo>>

    suspend fun getCoinInfo(id: String): CoinInfo

}