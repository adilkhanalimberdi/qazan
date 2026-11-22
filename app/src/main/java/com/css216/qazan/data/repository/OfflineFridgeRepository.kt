package com.css216.qazan.data.repository

import com.css216.qazan.data.local.FridgeDao
import com.css216.qazan.data.local.FridgeEntity
import com.css216.qazan.data.model.IngredientMatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class OfflineFridgeRepository(private val dao: FridgeDao) : FridgeRepository {
    override val ingredients = dao.observeAll().map { rows -> rows.map { it.name } }

    override suspend fun add(name: String) {
        val cleaned = name.trim().replace(Regex("\\s+"), " ")
        if (cleaned.isBlank()) return
        val existing = dao.observeAll().first()
        if (existing.none { IngredientMatcher.matches(it.name, cleaned) }) {
            dao.insert(FridgeEntity(cleaned))
        }
    }

    override suspend fun remove(name: String) {
        dao.observeAll().first().filter { IngredientMatcher.matches(it.name, name) }
            .forEach { dao.delete(it.name) }
    }
}
