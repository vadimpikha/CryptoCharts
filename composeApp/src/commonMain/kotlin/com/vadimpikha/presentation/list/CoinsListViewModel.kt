package com.vadimpikha.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadimpikha.domain.usecase.GetCryptoCoinsInfoFlowUseCase
import com.vadimpikha.presentation.list.models.UiEffect
import com.vadimpikha.presentation.list.models.UiEvent
import com.vadimpikha.presentation.list.models.UiState
import com.vadimpikha.presentation.utils.WhileSubscribedDefault
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class CoinsListViewModel(
    private val getCryptoCoinsInfoFlowUseCase: GetCryptoCoinsInfoFlowUseCase
) : ViewModel() {

    private val loadListTriggerFlow = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    val uiState = createUiStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribedDefault, UiState())

    private val _uiEffect = MutableSharedFlow<UiEffect>(extraBufferCapacity = 1)
    val uiEffect = _uiEffect.asSharedFlow()

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.OnCoinClicked -> _uiEffect.tryEmit(UiEffect.OpenCoinDetailsScreen(event.id))
        }
    }

    private fun createUiStateFlow(): Flow<UiState> {
        return loadListTriggerFlow
            .onStart { emit(Unit) }
            .flatMapLatest { getCryptoCoinsInfoFlowUseCase() }
            .map { UiState(it) }
    }
}