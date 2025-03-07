package com.vadimpikha.presentation.navigation

import kotlinx.serialization.Serializable

object Destinations {

    @Serializable
    data object CoinsList

    @Serializable
    data class CoinDetails(val id: String)
}