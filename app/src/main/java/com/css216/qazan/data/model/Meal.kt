package com.css216.qazan.data.model

data class Meal(
    val id: String,
    val name: String,
    val category: String,
    val area: String,
    val instructions: String,
    val thumbUrl: String?,
    val ingredients: List<Ingredient>
) {
    fun toSummary() = MealSummary(id, name, thumbUrl, category, area)
}
