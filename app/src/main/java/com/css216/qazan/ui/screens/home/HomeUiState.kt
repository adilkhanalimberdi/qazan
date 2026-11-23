package com.css216.qazan.ui.screens.home

import com.css216.qazan.data.model.Category
import com.css216.qazan.data.model.Meal

data class HomeUiState(
    val content: HomeContent = HomeContent.Loading,
    val fridgeCount: Int = 0,
    val favoriteIds: Set<String> = emptySet(),
    val darkTheme: Boolean? = null
)

sealed interface HomeContent {
    data object Loading : HomeContent
    data class Success(val meal: Meal, val categories: List<Category>) : HomeContent
    data object Error : HomeContent
}
