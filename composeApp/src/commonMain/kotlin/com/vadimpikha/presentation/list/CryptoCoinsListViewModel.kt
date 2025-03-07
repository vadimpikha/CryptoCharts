package com.vadimpikha.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadimpikha.domain.model.mapToLoadableResource
import com.vadimpikha.domain.usecase.GetCryptoCoinsInfoFlowUseCase
import com.vadimpikha.presentation.utils.WhileSubscribedDefault
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class CryptoCoinsListViewModel(
    private val getCryptoCoinsInfoFlowUseCase: GetCryptoCoinsInfoFlowUseCase
): ViewModel() {

    private val loadListTriggerFlow = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    val uiState = createUiStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribedDefault, UiState())


    private fun createUiStateFlow(): Flow<UiState> {
        return loadListTriggerFlow
            .onStart { emit(Unit) }
            .flatMapLatest {
                getCryptoCoinsInfoFlowUseCase()
                    .mapToLoadableResource()
                    .map { UiState(it) }
            }
    }
}