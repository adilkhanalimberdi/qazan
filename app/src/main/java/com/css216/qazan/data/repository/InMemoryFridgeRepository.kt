package com.css216.qazan.data.repository

import com.css216.qazan.data.model.IngredientMatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryFridgeRepository : FridgeRepository {
    private val _ingredients = MutableStateFlow<List<String>>(emptyList())
    override val ingredients = _ingredients.asStateFlow()

    override suspend fun add(name: String) {
        val cleaned = name.trim().replace(Regex("\\s+"), " ")
        if (cleaned.isBlank()) return
        _ingredients.update { current ->
            if (current.any { IngredientMatcher.matches(it, cleaned) }) current else current + cleaned
        }
    }

    override suspend fun remove(name: String) {
        _ingredients.update { current -> current.filterNot { IngredientMatcher.matches(it, name) } }
    }
}
