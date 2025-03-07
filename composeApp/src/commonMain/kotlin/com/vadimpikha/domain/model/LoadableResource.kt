package com.vadimpikha.domain.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface LoadableResource<out T> {
    data object Loading : LoadableResource<Nothing>
    data class Data<T>(val data: T) : LoadableResource<T>
    data class Error(val throwable: Throwable) : LoadableResource<Nothing>
}

@Suppress("USELESS_CAST")
fun <T> Flow<T>.mapToLoadableResource(): Flow<LoadableResource<T>> =
    map { LoadableResource.Data(it) as LoadableResource<T> }
        .onStart { emit(LoadableResource.Loading) }
        .catch { emit(LoadableResource.Error(it)) }