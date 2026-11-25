package com.css216.qazan.ui.screens.favorites

import com.css216.qazan.data.model.MealSummary
import com.css216.qazan.ui.components.MealPreviewState

data class FavoritesUiState(
    val meals: List<MealSummary> = emptyList(),
    val shoppingCount: Int = 0,
    val selectedMealId: String? = null,
    val preview: MealPreviewState? = null
)
