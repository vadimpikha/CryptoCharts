package com.vadimpikha.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadimpikha.domain.model.mapToPendingResult
import com.vadimpikha.domain.network.model.CoinsSorting
import com.vadimpikha.domain.network.model.OrderBy
import com.vadimpikha.domain.usecase.GetCoinsInfoFlowUseCase
import com.vadimpikha.presentation.list.models.UiEffect
import com.vadimpikha.presentation.list.models.UiEvent
import com.vadimpikha.presentation.list.models.UiState
import com.vadimpikha.presentation.utils.WhileSubscribedDefault
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalCoroutinesApi::class)
class CoinsListViewModel(
    private val getCoinsInfoFlowUseCase: GetCoinsInfoFlowUseCase
) : ViewModel() {

    private val loadListTriggerFlow = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    private val coinsSorting = MutableStateFlow(CoinsSorting.Default)

    val uiState = createUiStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribedDefault,
            UiState(sorting = coinsSorting.value)
        )

    private val _uiEffect = MutableSharedFlow<UiEffect>(extraBufferCapacity = 1)
    val uiEffect = _uiEffect.asSharedFlow()

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.OnCoinClicked -> _uiEffect.tryEmit(UiEffect.OpenCoinDetailsScreen(event.id))
            is UiEvent.OnSortingOrderClicked -> onSortingOrderClicked(event.orderBy)
        }
    }

    private fun onSortingOrderClicked(orderBy: OrderBy) {
        coinsSorting.update { currentSorting ->
            if (currentSorting.orderBy == orderBy) {
                currentSorting.copyWith(ascending = !currentSorting.ascending)
            } else {
                CoinsSorting.By(orderBy)
            }
        }
    }

    private fun createUiStateFlow(): Flow<UiState> {
        return loadListTriggerFlow
            .onStart { emit(Unit) }
            .combine(coinsSorting) { _, sorting -> sorting }
            .flatMapLatest { sorting -> getCoinsInfoFlowUseCase(sorting) }
            .mapToPendingResult()
            .map { UiState(it, coinsSorting.value) }
    }
}