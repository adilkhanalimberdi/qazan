package com.css216.qazan.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.css216.qazan.data.model.MealSummary
import com.css216.qazan.data.repository.FavoritesRepository
import com.css216.qazan.data.repository.RecipeRepository
import com.css216.qazan.data.repository.ShoppingRepository
import com.css216.qazan.ui.components.MealPreviewState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository,
    shoppingRepository: ShoppingRepository,
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()
    private var previewJob: Job? = null

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

    fun selectMeal(id: String) {
        previewJob?.cancel()
        previewJob = viewModelScope.launch {
            _uiState.update { it.copy(selectedMealId = id, preview = MealPreviewState.Loading) }
            try {
                val meal = recipeRepository.getMealById(id)
                _uiState.update { it.copy(preview = MealPreviewState.Success(meal)) }
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                _uiState.update { it.copy(preview = MealPreviewState.Error) }
            }
        }
    }

    fun retrySelectedMeal() {
        _uiState.value.selectedMealId?.let(::selectMeal)
    }
}
