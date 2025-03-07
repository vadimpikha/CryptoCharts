package com.vadimpikha.presentation.list.models

sealed interface UiEffect {
    data class OpenCoinDetailsScreen(val id: String): UiEffect
}