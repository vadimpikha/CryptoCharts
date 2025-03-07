package com.vadimpikha.presentation.utils

import kotlinx.coroutines.flow.SharingStarted

val SharingStarted.Companion.WhileSubscribedDefault
    get() = WhileSubscribed(stopTimeoutMillis = 5_000)