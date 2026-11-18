package com.css216.qazan.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.css216.qazan.data.model.IngredientMatcher
import com.css216.qazan.data.repository.FavoritesRepository
import com.css216.qazan.data.repository.FridgeRepository
import com.css216.qazan.data.repository.RecipeRepository
import com.css216.qazan.data.repository.ShoppingRepository
import com.css216.qazan.ui.navigation.QazanDestination
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository,
    private val favoritesRepository: FavoritesRepository,
    private val fridgeRepository: FridgeRepository,
    private val shoppingRepository: ShoppingRepository
) : ViewModel() {
    private val mealId: String = savedStateHandle[QazanDestination.Detail.ARG] ?: ""
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()
    private var loadJob: Job? = null

    init { loadMeal() }

    fun loadMeal() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.update { it.copy(content = DetailContent.Loading) }
            try {
                val meal = recipeRepository.getMealById(mealId)
                combine(fridgeRepository.ingredients, favoritesRepository.isFavorite(mealId), shoppingRepository.items) { fridge, isFavorite, shopping ->
                    val have = meal.ingredients.filter { ingredient ->
                        fridge.any { IngredientMatcher.matches(it, ingredient.name) }
                    }.map { it.name }.toSet()
                    val missing = meal.ingredients.filterNot { it.name in have }
                    val alreadyAdded = missing.isNotEmpty() && missing.all { ingredient ->
                        shopping.any { it.source == meal.name && it.measure == ingredient.measure && IngredientMatcher.matches(it.name, ingredient.name) }
                    }
                    DetailContent.Success(meal, have, isFavorite, missing) to alreadyAdded
                }.collect { (content, alreadyAdded) ->
                    _uiState.update { it.copy(content = content, addedToShopping = alreadyAdded) }
                }
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                _uiState.update { it.copy(content = DetailContent.Error) }
            }
        }
    }

    fun selectTab(tab: DetailTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun toggleFavorite() {
        val content = _uiState.value.content as? DetailContent.Success ?: return
        viewModelScope.launch { favoritesRepository.toggle(content.meal.toSummary()) }
    }

    fun addMissingToShopping() {
        val content = _uiState.value.content as? DetailContent.Success ?: return
        viewModelScope.launch { shoppingRepository.addAll(content.missing, content.meal.name) }
    }
}
