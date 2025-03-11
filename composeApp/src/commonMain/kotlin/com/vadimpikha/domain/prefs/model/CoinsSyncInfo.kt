package com.vadimpikha.domain.prefs.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinsSyncInfo(
    @SerialName("timestamp")
    val timestamp: Instant,
)