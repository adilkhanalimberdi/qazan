package com.css216.qazan.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.css216.qazan.data.model.MealSummary
import com.css216.qazan.data.repository.FavoritesRepository
import com.css216.qazan.data.repository.ShoppingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository,
    shoppingRepository: ShoppingRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(favoritesRepository.favorites, shoppingRepository.items) { meals, items ->
                meals to items.count { !it.isBought }
            }.collect { (meals, count) ->
                _uiState.update { it.copy(meals = meals, shoppingCount = count) }
            }
        }
    }

    fun toggleFavorite(meal: MealSummary) {
        viewModelScope.launch { favoritesRepository.toggle(meal) }
    }
}
