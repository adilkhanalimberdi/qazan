package com.css216.qazan.data.repository

import com.css216.qazan.data.local.FavoriteDao
import com.css216.qazan.data.local.FavoriteEntity
import com.css216.qazan.data.model.MealSummary
import kotlinx.coroutines.flow.map

class OfflineFavoritesRepository(private val dao: FavoriteDao) : FavoritesRepository {
    override val favorites = dao.observeAll().map { meals -> meals.map { it.toDomain() } }

    override fun isFavorite(id: String) = dao.observeFavorite(id)

    override suspend fun toggle(meal: MealSummary) {
        if (dao.contains(meal.id)) dao.delete(meal.id) else dao.insert(meal.toEntity())
    }
}

private fun FavoriteEntity.toDomain() = MealSummary(id, name, thumbUrl, category, area)

private fun MealSummary.toEntity() = FavoriteEntity(id, name, thumbUrl, category, area)
