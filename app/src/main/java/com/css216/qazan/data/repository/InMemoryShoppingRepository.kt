package com.css216.qazan.data.repository

import com.css216.qazan.data.model.Ingredient
import com.css216.qazan.data.model.IngredientMatcher
import com.css216.qazan.data.model.ShoppingItem
import java.util.concurrent.atomic.AtomicLong
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryShoppingRepository : ShoppingRepository {
    private val _items = MutableStateFlow<List<ShoppingItem>>(emptyList())
    override val items = _items.asStateFlow()
    private val nextId = AtomicLong(1)

    override suspend fun addAll(items: List<Ingredient>, source: String) {
        val additions = items.filter { it.name.isNotBlank() }
            .distinctBy { IngredientMatcher.normalize(it.name) to it.measure }
            .map { ShoppingItem(nextId.getAndIncrement(), it.name.trim(), it.measure, source) }
        _items.update { current ->
            current + additions.filter { newItem ->
                current.none {
                    it.source == source && it.measure == newItem.measure &&
                        IngredientMatcher.matches(it.name, newItem.name)
                }
            }
        }
    }

    override suspend fun setBought(id: Long, bought: Boolean) {
        _items.update { current -> current.map { if (it.id == id) it.copy(isBought = bought) else it } }
    }

    override suspend fun clearBought() {
        _items.update { current -> current.filterNot { it.isBought } }
    }
}
