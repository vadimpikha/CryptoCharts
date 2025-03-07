package com.vadimpikha.domain.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

interface DispatcherProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
}

object DefaultDispatcherProvider : DispatcherProvider {
    override val io = Dispatchers.IO
    override val main = Dispatchers.Main
    override val default = Dispatchers.Default
}
