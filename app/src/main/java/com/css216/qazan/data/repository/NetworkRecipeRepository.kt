package com.css216.qazan.data.repository

import com.css216.qazan.data.model.Category
import com.css216.qazan.data.model.MatchedMeal
import com.css216.qazan.data.model.Meal
import com.css216.qazan.data.model.MealSummary
import com.css216.qazan.data.network.MealDbApiService
import com.css216.qazan.data.network.toDomain

class NetworkRecipeRepository(private val api: MealDbApiService) : RecipeRepository {
    override suspend fun getRandomMeal(): Meal =
        api.random().meals.orEmpty().firstOrNull()?.toDomain()
            ?: throw NoSuchElementException("No random meal returned")

    override suspend fun getCategories(): List<Category> =
        api.categories().categories.orEmpty().map { it.toDomain() }

    override suspend fun getMealById(id: String): Meal =
        api.lookup(id).meals.orEmpty().firstOrNull()?.toDomain()
            ?: throw NoSuchElementException("Unknown meal: $id")

    override suspend fun searchByName(query: String): List<MealSummary> =
        api.search(query.trim()).meals.orEmpty().map { it.toDomain().toSummary() }

    override suspend fun getMealsByCategory(category: String): List<MealSummary> =
        api.filterByCategory(category).meals.orEmpty().map { it.toDomain() }

    override suspend fun findByIngredients(ingredients: List<String>): List<MatchedMeal> {
        val selected = ingredients.map { it.trim() }.filter { it.isNotEmpty() }.distinct()
        if (selected.isEmpty()) return emptyList()
        val matches = mutableMapOf<String, MatchedMeal>()
        for (ingredient in selected) {
            val query = ingredient.lowercase().replace(' ', '_')
            for (meal in api.filterByIngredient(query).meals.orEmpty()) {
                val previous = matches[meal.id]
                matches[meal.id] = MatchedMeal(
                    meal = meal.toDomain(),
                    matchedCount = (previous?.matchedCount ?: 0) + 1,
                    selectedCount = selected.size
                )
            }
        }
        return matches.values.sortedWith(
            compareByDescending<MatchedMeal> { it.matchedCount }.thenBy { it.meal.name }
        )
    }
}
