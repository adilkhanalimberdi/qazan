package com.css216.qazan.data.repository

import com.css216.qazan.data.model.Category
import com.css216.qazan.data.model.MatchedMeal
import com.css216.qazan.data.model.Meal
import com.css216.qazan.data.model.MealSummary

interface RecipeRepository {
    suspend fun getRandomMeal(): Meal
    suspend fun getCategories(): List<Category>
    suspend fun getMealById(id: String): Meal
    suspend fun searchByName(query: String): List<MealSummary>
    suspend fun getMealsByCategory(category: String): List<MealSummary>
    suspend fun findByIngredients(ingredients: List<String>): List<MatchedMeal>
}
