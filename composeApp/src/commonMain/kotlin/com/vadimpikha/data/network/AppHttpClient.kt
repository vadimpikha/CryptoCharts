package com.vadimpikha.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal val AppHttpClient = HttpClient {
    install(Logging) {
        level = LogLevel.ALL
    }

    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            }
        )
    }
}