package com.vadimpikha.domain.prefs

import com.vadimpikha.domain.prefs.model.CoinsSyncInfo

interface PrefsManager {

    suspend fun getCoinsSyncInfo(): CoinsSyncInfo?

    suspend fun updateCoinsSyncInfo(info: CoinsSyncInfo?)

}