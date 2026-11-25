package com.css216.qazan.ui.screens.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.css216.qazan.data.model.MatchedMeal
import com.css216.qazan.data.model.MealSummary
import com.css216.qazan.data.repository.FavoritesRepository
import com.css216.qazan.data.repository.FridgeRepository
import com.css216.qazan.data.repository.RecipeRepository
import com.css216.qazan.ui.navigation.QazanDestination
import com.css216.qazan.ui.components.MealPreviewState
import java.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SearchViewModel(
    savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository,
    fridgeRepository: FridgeRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    private val initialCategory: String = savedStateHandle[QazanDestination.Search.ARG] ?: ""
    private val _uiState = MutableStateFlow(SearchUiState(category = initialCategory, useFridge = initialCategory.isBlank()))
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    private var fridgeIngredients = emptyList<String>()
    private var searchJob: Job? = null
    private var previewJob: Job? = null

    init {
        viewModelScope.launch {
            fridgeRepository.ingredients.collect { ingredients ->
                fridgeIngredients = ingredients
                _uiState.update { it.copy(fridgeCount = ingredients.size) }
                loadResults()
            }
        }
        viewModelScope.launch {
            favoritesRepository.favorites.collect { favorites ->
                _uiState.update { it.copy(favoriteIds = favorites.map { meal -> meal.id }.toSet()) }
            }
        }
    }

    fun updateQuery(query: String) {
        _uiState.update { it.copy(query = query) }
        loadResults()
    }

    fun toggleFridge() {
        _uiState.update { it.copy(useFridge = !it.useFridge) }
        loadResults()
    }

    fun clearCategory() {
        _uiState.update { it.copy(category = "") }
        loadResults()
    }

    fun loadResults() {
        searchJob?.cancel()
        val state = _uiState.value
        val selected = fridgeIngredients
        searchJob = viewModelScope.launch {
            _uiState.update { it.copy(content = SearchContent.Loading) }
            try {
                if (state.query.isNotBlank()) delay(300)
                val results = if (state.useFridge) {
                    recipeRepository.findByIngredients(selected)
                } else {
                    recipeRepository.searchByName(state.query).map { MatchedMeal(it, 0, 0) }.sortedBy { it.meal.name }
                }
                val categoryIds = if (state.category.isBlank()) null
                    else recipeRepository.getMealsByCategory(state.category).map { it.id }.toSet()
                val filtered = results.filter {
                    it.meal.name.contains(state.query.trim(), ignoreCase = true) &&
                        (categoryIds == null || it.meal.id in categoryIds)
                }
                _uiState.update { it.copy(content = SearchContent.Success(filtered)) }
            } catch (error: CancellationException) {
                throw error
            } catch (_: IOException) {
                _uiState.update { it.copy(content = SearchContent.Error) }
            } catch (_: HttpException) {
                _uiState.update { it.copy(content = SearchContent.Error) }
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
