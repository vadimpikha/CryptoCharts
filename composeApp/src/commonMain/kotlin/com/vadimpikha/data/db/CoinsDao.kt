package com.vadimpikha.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vadimpikha.data.db.model.CoinInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinsInfo(coinsInfo: List<CoinInfoEntity>)

    @Query("SELECT * FROM coins_info")
    fun getCoinsInfoFlow(): Flow<List<CoinInfoEntity>>

    @Query("SELECT * FROM coins_info WHERE id=:id")
    suspend fun getCoinInfo(id: String): CoinInfoEntity

    @Query("DELETE FROM coins_info")
    suspend fun clearCoinsInfo()
}