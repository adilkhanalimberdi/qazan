package com.css216.qazan.data.network

import com.css216.qazan.data.model.Category
import com.css216.qazan.data.model.Ingredient
import com.css216.qazan.data.model.Meal
import com.css216.qazan.data.model.MealSummary

fun MealSummaryDto.toDomain() = MealSummary(id, name, thumb)

fun CategoryDto.toDomain() = Category(name, thumb)

fun MealDto.toDomain(): Meal {
    val names = listOf(
        strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
        strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
        strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15,
        strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient20
    )
    val measures = listOf(
        strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
        strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
        strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15,
        strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure20
    )
    val ingredients = names.mapIndexedNotNull { index, rawName ->
        rawName?.trim()?.takeIf { it.isNotEmpty() }?.let { name ->
            Ingredient(name, measures[index]?.trim().orEmpty())
        }
    }
    return Meal(
        id = id,
        name = name,
        category = category.orEmpty(),
        area = area.orEmpty(),
        instructions = instructions.orEmpty(),
        thumbUrl = thumb,
        ingredients = ingredients
    )
}
