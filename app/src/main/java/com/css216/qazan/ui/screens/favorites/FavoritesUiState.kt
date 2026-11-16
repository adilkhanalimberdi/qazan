package com.css216.qazan.ui.screens.favorites

import com.css216.qazan.data.model.MealSummary

data class FavoritesUiState(
    val meals: List<MealSummary> = emptyList(),
    val shoppingCount: Int = 0
)
