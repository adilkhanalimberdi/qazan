package com.css216.qazan.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fridge")
data class FridgeEntity(
    @PrimaryKey val name: String
)
