package com.vadimpikha.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.ReadOnlyComposable

@ReadOnlyComposable
@Composable
fun <T> CompositionLocal<T?>.currentOrThrow(): T {
    return current ?: throw IllegalStateException()
}