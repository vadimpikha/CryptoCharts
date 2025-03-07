package com.vadimpikha.domain.usecase

import com.vadimpikha.domain.model.PendingResult
import com.vadimpikha.domain.model.mapToPendingResult
import com.vadimpikha.domain.network.CryptoInfoRepository
import com.vadimpikha.domain.network.model.CoinInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetCryptoCoinsInfoFlowUseCase(
    private val cryptoInfoRepository: CryptoInfoRepository
) {

    operator fun invoke(): Flow<PendingResult<List<CoinInfo>>> {
        return flow { emit(cryptoInfoRepository.getCoinsInfo()) }
            .mapToPendingResult()
    }

}