package com.css216.qazan.data.repository

import com.css216.qazan.data.model.MealSummary
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    val favorites: Flow<List<MealSummary>>
    fun isFavorite(id: String): Flow<Boolean>
    suspend fun toggle(meal: MealSummary)
}
