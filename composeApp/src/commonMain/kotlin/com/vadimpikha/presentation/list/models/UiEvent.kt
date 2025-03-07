package com.vadimpikha.presentation.list.models

sealed interface UiEvent {
    data class OnCoinClicked(val id: String): UiEvent
}