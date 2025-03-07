package com.vadimpikha.presentation.list

import com.vadimpikha.domain.model.LoadableResource
import com.vadimpikha.domain.network.model.CoinInfo

data class UiState(
    val coinsInfo: LoadableResource<List<CoinInfo>> = LoadableResource.Loading
)