package com.css216.qazan.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: String,
    val name: String,
    val thumbUrl: String?,
    val category: String,
    val area: String
)
