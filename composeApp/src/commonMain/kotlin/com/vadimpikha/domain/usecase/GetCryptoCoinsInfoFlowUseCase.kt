package com.vadimpikha.domain.usecase

import com.vadimpikha.domain.model.PendingResult
import com.vadimpikha.domain.model.mapToPendingResult
import com.vadimpikha.domain.network.CryptoInfoRepository
import com.vadimpikha.domain.network.model.CoinInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetCryptoCoinsInfoFlowUseCase(
    private val cryptoInfoRepository: CryptoInfoRepository
) {

    operator fun invoke(): Flow<PendingResult<List<CoinInfo>>> {
        return cryptoInfoRepository.getCoinsInfoFlow(forceFetch = true)
            .map { it.sortedBy (CoinInfo::marketCapRank) }
            .mapToPendingResult()
    }

}