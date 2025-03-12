package com.vadimpikha.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadimpikha.domain.model.PendingResult
import com.vadimpikha.domain.model.mapToPendingResult
import com.vadimpikha.domain.usecase.GetCoinInfoUseCase
import com.vadimpikha.presentation.utils.WhileSubscribedDefault
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class CoinDetailsViewModel(
    private val coinId: String,
    private val getCoinInfoUseCase: GetCoinInfoUseCase
): ViewModel() {

    val info = flow { emit(getCoinInfoUseCase(coinId)) }
        .mapToPendingResult()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribedDefault, PendingResult.Loading)
}