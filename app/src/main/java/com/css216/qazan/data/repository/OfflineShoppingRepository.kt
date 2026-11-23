package com.css216.qazan.data.repository

import com.css216.qazan.data.local.ShoppingDao
import com.css216.qazan.data.local.ShoppingEntity
import com.css216.qazan.data.model.Ingredient
import com.css216.qazan.data.model.IngredientMatcher
import com.css216.qazan.data.model.ShoppingItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class OfflineShoppingRepository(private val dao: ShoppingDao) : ShoppingRepository {
    override val items = dao.observeAll().map { rows -> rows.map { it.toDomain() } }

    override suspend fun addAll(items: List<Ingredient>, source: String) {
        val current = dao.observeAll().first().toMutableList()
        for (ingredient in items) {
            val name = ingredient.name.trim()
            if (name.isBlank()) continue
            if (current.any {
                it.source == source && it.measure == ingredient.measure &&
                    IngredientMatcher.matches(it.name, name)
            }) continue
            val row = ShoppingEntity(name = name, measure = ingredient.measure, source = source)
            dao.insert(row)
            current += row
        }
    }

    override suspend fun setBought(id: Long, bought: Boolean) = dao.setBought(id, bought)

    override suspend fun clearBought() = dao.clearBought()
}

private fun ShoppingEntity.toDomain() = ShoppingItem(id, name, measure, source, isBought)
