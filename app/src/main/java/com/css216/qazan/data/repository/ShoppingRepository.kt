package com.css216.qazan.data.repository

import com.css216.qazan.data.model.Ingredient
import com.css216.qazan.data.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
    val items: Flow<List<ShoppingItem>>
    suspend fun addAll(items: List<Ingredient>, source: String)
    suspend fun setBought(id: Long, bought: Boolean)
    suspend fun clearBought()
}
