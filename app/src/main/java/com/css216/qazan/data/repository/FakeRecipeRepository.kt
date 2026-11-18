package com.css216.qazan.data.repository

import com.css216.qazan.data.fake.FakeRecipeData
import com.css216.qazan.data.model.Category
import com.css216.qazan.data.model.IngredientMatcher
import com.css216.qazan.data.model.MatchedMeal
import com.css216.qazan.data.model.Meal
import com.css216.qazan.data.model.MealSummary

class FakeRecipeRepository : RecipeRepository {
    private val meals = FakeRecipeData.meals
    private var nextMeal = 0

    override suspend fun getRandomMeal(): Meal {
        val meal = meals[nextMeal]
        nextMeal = (nextMeal + 1) % meals.size
        return meal
    }

    override suspend fun getCategories(): List<Category> =
        meals.map { Category(it.category, null) }.distinctBy { it.name }

    override suspend fun getMealById(id: String): Meal =
        meals.firstOrNull { it.id == id } ?: throw NoSuchElementException("Unknown meal: $id")

    override suspend fun searchByName(query: String): List<MealSummary> =
        meals.filter { it.name.contains(query.trim(), ignoreCase = true) }.map { it.toSummary() }

    override suspend fun getMealsByCategory(category: String): List<MealSummary> =
        meals.filter { it.category.equals(category, ignoreCase = true) }.map { it.toSummary() }

    override suspend fun getIngredientSuggestions(): List<String> =
        meals.flatMap { it.ingredients }.map { it.name }.distinct().sorted()

    override suspend fun findByIngredients(ingredients: List<String>): List<MatchedMeal> {
        val selected = ingredients.map(IngredientMatcher::normalize).filter { it.isNotBlank() }.toSet()
        if (selected.isEmpty()) return emptyList()
        return meals.map { meal ->
            val names = meal.ingredients.map { IngredientMatcher.normalize(it.name) }.toSet()
            MatchedMeal(meal.toSummary(), selected.count { it in names }, selected.size)
        }.filter { it.matchedCount > 0 }
            .sortedWith(compareByDescending<MatchedMeal> { it.matchedCount }.thenBy { it.meal.name })
    }
}
