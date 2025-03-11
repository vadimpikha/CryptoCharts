package com.vadimpikha.presentation.utils

import kotlin.math.ceil
import kotlin.math.pow

fun Double.ceilToDecimals(decimals: Int): Double {
    val dotAt = 10.0.pow(decimals)
    return ceil(this * dotAt) / dotAt
}