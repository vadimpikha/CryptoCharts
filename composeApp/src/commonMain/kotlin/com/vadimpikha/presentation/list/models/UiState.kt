package com.vadimpikha.presentation.list.models

import com.vadimpikha.domain.model.PendingResult
import com.vadimpikha.domain.network.model.CoinInfo

data class UiState(
    val coinsInfo: PendingResult<List<CoinInfo>> = PendingResult.Loading
)