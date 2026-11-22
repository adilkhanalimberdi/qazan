package com.css216.qazan.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {
    @Query("SELECT * FROM shopping ORDER BY id")
    fun observeAll(): Flow<List<ShoppingEntity>>

    @Insert
    suspend fun insert(item: ShoppingEntity)

    @Query("UPDATE shopping SET isBought = :bought WHERE id = :id")
    suspend fun setBought(id: Long, bought: Boolean)

    @Query("DELETE FROM shopping WHERE isBought = 1")
    suspend fun clearBought()
}
