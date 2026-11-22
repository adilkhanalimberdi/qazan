package com.css216.qazan.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping")
data class ShoppingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val measure: String,
    val source: String?,
    val isBought: Boolean = false
)
