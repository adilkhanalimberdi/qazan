package com.css216.qazan.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteEntity::class, FridgeEntity::class, ShoppingEntity::class],
    version = 1,
    exportSchema = false
)
abstract class QazanDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun fridgeDao(): FridgeDao
    abstract fun shoppingDao(): ShoppingDao
}
