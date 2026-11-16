package com.css216.qazan.ui.screens.detail

import com.css216.qazan.data.model.Ingredient
import com.css216.qazan.data.model.Meal

data class DetailUiState(
    val content: DetailContent = DetailContent.Loading,
    val selectedTab: DetailTab = DetailTab.Ingredients,
    val addedToShopping: Boolean = false
)

enum class DetailTab { Ingredients, Instructions }

sealed interface DetailContent {
    data object Loading : DetailContent
    data class Success(
        val meal: Meal,
        val have: Set<String> = emptySet(),
        val isFavorite: Boolean = false,
        val missing: List<Ingredient> = meal.ingredients
    ) : DetailContent
    data object Error : DetailContent
}
