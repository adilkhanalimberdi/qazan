package com.css216.qazan.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.css216.qazan.data.repository.FavoritesRepository
import com.css216.qazan.data.repository.FridgeRepository
import com.css216.qazan.data.repository.RecipeRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val recipeRepository: RecipeRepository,
    fridgeRepository: FridgeRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    private var loadJob: Job? = null

    init {
        loadRecipe()
        viewModelScope.launch {
            fridgeRepository.ingredients.collect { ingredients ->
                _uiState.update { it.copy(fridgeCount = ingredients.size) }
            }
        }
        viewModelScope.launch {
            favoritesRepository.favorites.collect { favorites ->
                _uiState.update { it.copy(favoriteIds = favorites.map { meal -> meal.id }.toSet()) }
            }
        }
    }

    fun loadRecipe() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.update { it.copy(content = HomeContent.Loading) }
            try {
                val meal = recipeRepository.getRandomMeal()
                val categories = recipeRepository.getCategories()
                _uiState.update { it.copy(content = HomeContent.Success(meal, categories)) }
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                _uiState.update { it.copy(content = HomeContent.Error) }
            }
        }
    }

    fun toggleFavorite() {
        val content = _uiState.value.content as? HomeContent.Success ?: return
        viewModelScope.launch { favoritesRepository.toggle(content.meal.toSummary()) }
    }
}
