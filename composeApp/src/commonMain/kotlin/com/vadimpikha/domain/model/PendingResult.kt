package com.vadimpikha.domain.model

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

@Stable
sealed interface PendingResult<out T> {
    data object Loading : PendingResult<Nothing>
    data class Data<T>(val data: T) : PendingResult<T>
    data class Error(val throwable: Throwable) : PendingResult<Nothing>
}

fun <T> PendingResult<T>.getOrThrow(): T {
    return when (this) {
        is PendingResult.Data<T> -> data
        is PendingResult.Error -> throw throwable
        PendingResult.Loading -> throw RuntimeException("Data isn't loaded")
    }
}

fun <T> PendingResult<T>.getOrNull(): T? {
    if (this is PendingResult.Data<T>) return data

    return null
}

@Suppress("USELESS_CAST")
fun <T> Flow<T>.mapToPendingResult(): Flow<PendingResult<T>> {
    return map { PendingResult.Data(it) as PendingResult<T> }
        .onStart { emit(PendingResult.Loading) }
        .catch { emit(PendingResult.Error(it)) }
}

suspend fun <T> Flow<PendingResult<T>>.waitData(): T {
    return filterIsInstance<PendingResult.Data<T>>().first().data
}

suspend fun <T> Flow<PendingResult<T>>.waitResult(): Result<T> {
    val result = first { it !is PendingResult.Loading }
    return runCatching { result.getOrThrow() }
}