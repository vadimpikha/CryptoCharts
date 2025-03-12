package com.vadimpikha.presentation.utils

import kotlin.math.floor
import kotlin.math.pow

fun Double.floorToDecimals(decimals: Int): Double {
    require(decimals >= 0)
    if (decimals == 0) return floor(this)

    val dotAt = 10.0.pow(decimals)
    return floor(this * dotAt) / dotAt
}