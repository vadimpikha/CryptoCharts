package com.vadimpikha.presentation.list.models

import com.vadimpikha.domain.network.model.OrderBy

sealed interface UiEvent {
    data class OnCoinClicked(val id: String): UiEvent
    data class OnSortingOrderClicked(val orderBy: OrderBy): UiEvent
}