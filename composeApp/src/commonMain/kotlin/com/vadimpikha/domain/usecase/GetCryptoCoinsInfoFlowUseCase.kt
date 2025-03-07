package com.vadimpikha.domain.usecase

import com.vadimpikha.domain.network.CryptoInfoRepository
import com.vadimpikha.domain.network.model.CoinInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCryptoCoinsInfoFlowUseCase(
    private val cryptoInfoRepository: CryptoInfoRepository
) {

    operator fun invoke(): Flow<List<CoinInfo>> {
        return flow {
            emit(cryptoInfoRepository.getCoinsInfo())
        }
    }

}