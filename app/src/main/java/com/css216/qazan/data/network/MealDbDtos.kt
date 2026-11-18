package com.css216.qazan.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealsResponse(val meals: List<MealDto>? = null)

@Serializable
data class MealSummariesResponse(val meals: List<MealSummaryDto>? = null)

@Serializable
data class CategoriesResponse(val categories: List<CategoryDto>? = null)

@Serializable
data class IngredientListResponse(val meals: List<IngredientDto>? = null)

@Serializable
data class MealSummaryDto(
    @SerialName("idMeal") val id: String,
    @SerialName("strMeal") val name: String,
    @SerialName("strMealThumb") val thumb: String? = null
)

@Serializable
data class CategoryDto(
    @SerialName("strCategory") val name: String,
    @SerialName("strCategoryThumb") val thumb: String? = null
)

@Serializable
data class IngredientDto(
    @SerialName("idIngredient") val id: String,
    @SerialName("strIngredient") val name: String
)

@Serializable
data class MealDto(
    @SerialName("idMeal") val id: String,
    @SerialName("strMeal") val name: String,
    @SerialName("strCategory") val category: String? = null,
    @SerialName("strArea") val area: String? = null,
    @SerialName("strInstructions") val instructions: String? = null,
    @SerialName("strMealThumb") val thumb: String? = null,
    val strIngredient1: String? = null,
    val strMeasure1: String? = null,
    val strIngredient2: String? = null,
    val strMeasure2: String? = null,
    val strIngredient3: String? = null,
    val strMeasure3: String? = null,
    val strIngredient4: String? = null,
    val strMeasure4: String? = null,
    val strIngredient5: String? = null,
    val strMeasure5: String? = null,
    val strIngredient6: String? = null,
    val strMeasure6: String? = null,
    val strIngredient7: String? = null,
    val strMeasure7: String? = null,
    val strIngredient8: String? = null,
    val strMeasure8: String? = null,
    val strIngredient9: String? = null,
    val strMeasure9: String? = null,
    val strIngredient10: String? = null,
    val strMeasure10: String? = null,
    val strIngredient11: String? = null,
    val strMeasure11: String? = null,
    val strIngredient12: String? = null,
    val strMeasure12: String? = null,
    val strIngredient13: String? = null,
    val strMeasure13: String? = null,
    val strIngredient14: String? = null,
    val strMeasure14: String? = null,
    val strIngredient15: String? = null,
    val strMeasure15: String? = null,
    val strIngredient16: String? = null,
    val strMeasure16: String? = null,
    val strIngredient17: String? = null,
    val strMeasure17: String? = null,
    val strIngredient18: String? = null,
    val strMeasure18: String? = null,
    val strIngredient19: String? = null,
    val strMeasure19: String? = null,
    val strIngredient20: String? = null,
    val strMeasure20: String? = null
)
