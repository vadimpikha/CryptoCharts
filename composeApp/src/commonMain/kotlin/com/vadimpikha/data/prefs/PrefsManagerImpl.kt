package com.vadimpikha.data.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vadimpikha.domain.prefs.PrefsManager
import com.vadimpikha.domain.prefs.model.CoinsSyncInfo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PrefsManagerImpl(private val dataStore: DataStore<Preferences>) : PrefsManager {

    override suspend fun getCoinsSyncInfo(): CoinsSyncInfo? {
        return dataStore.data.map {
            Json.decodeFromString<CoinsSyncInfo>(it[Keys.CoinsSyncInfo] ?: return@map null)
        }.first()
    }

    override suspend fun updateCoinsSyncInfo(info: CoinsSyncInfo?) {
        dataStore.edit {
            if (info != null) {
                it[Keys.CoinsSyncInfo] = Json.encodeToString(info)
            } else {
                it.remove(Keys.CoinsSyncInfo)
            }
        }
    }

    private object Keys {
        val CoinsSyncInfo = stringPreferencesKey("coins_sync_info")
    }
}