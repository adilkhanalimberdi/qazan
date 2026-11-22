package com.css216.qazan.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FridgeDao {
    @Query("SELECT * FROM fridge ORDER BY name")
    fun observeAll(): Flow<List<FridgeEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ingredient: FridgeEntity)

    @Query("DELETE FROM fridge WHERE name = :name")
    suspend fun delete(name: String)
}
