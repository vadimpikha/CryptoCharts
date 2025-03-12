package com.vadimpikha.domain.usecase

import com.vadimpikha.domain.network.CryptoInfoRepository

class GetCoinInfoUseCase(
    private val repository: CryptoInfoRepository
) {
    suspend operator fun invoke(id: String) = repository.getCoinInfo(id)
}