package com.css216.qazan.ui.screens.search

import com.css216.qazan.data.model.MatchedMeal
import com.css216.qazan.ui.components.MealPreviewState

data class SearchUiState(
    val query: String = "",
    val category: String = "",
    val useFridge: Boolean = true,
    val fridgeCount: Int = 0,
    val favoriteIds: Set<String> = emptySet(),
    val content: SearchContent = SearchContent.Loading,
    val selectedMealId: String? = null,
    val preview: MealPreviewState? = null
)

sealed interface SearchContent {
    data object Loading : SearchContent
    data class Success(val results: List<MatchedMeal>) : SearchContent
    data object Error : SearchContent
}
