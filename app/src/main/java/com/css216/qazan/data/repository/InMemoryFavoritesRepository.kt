package com.css216.qazan.data.repository

import com.css216.qazan.data.model.MealSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class InMemoryFavoritesRepository : FavoritesRepository {
    private val _favorites = MutableStateFlow<List<MealSummary>>(emptyList())
    override val favorites = _favorites.asStateFlow()

    override fun isFavorite(id: String) =
        favorites.map { meals -> meals.any { it.id == id } }.distinctUntilChanged()

    override suspend fun toggle(meal: MealSummary) {
        _favorites.update { current ->
            if (current.any { it.id == meal.id }) current.filterNot { it.id == meal.id }
            else current + meal
        }
    }
}
