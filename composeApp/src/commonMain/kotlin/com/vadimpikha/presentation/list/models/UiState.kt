package com.vadimpikha.presentation.list.models

import com.vadimpikha.domain.model.PendingResult
import com.vadimpikha.domain.network.model.CoinInfo
import com.vadimpikha.domain.network.model.CoinsSorting

data class UiState(
    val coinsInfo: PendingResult<List<CoinInfo>> = PendingResult.Loading,
    val sorting: CoinsSorting
)