package com.css216.qazan.ui.screens.fridge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.css216.qazan.data.model.IngredientMatcher
import com.css216.qazan.data.repository.FridgeRepository
import com.css216.qazan.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FridgeViewModel(
    private val fridgeRepository: FridgeRepository,
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FridgeUiState())
    val uiState: StateFlow<FridgeUiState> = _uiState.asStateFlow()
    private var allSuggestions = listOf("Chicken", "Rice", "Tomato", "Egg", "Onion", "Potato", "Cheese", "Milk")

    init {
        viewModelScope.launch {
            fridgeRepository.ingredients.collect { ingredients ->
                _uiState.update { it.copy(ingredients = ingredients) }
                updateSuggestions()
            }
        }
        viewModelScope.launch {
            try {
                allSuggestions = recipeRepository.getIngredientSuggestions()
                updateSuggestions()
            } catch (_: java.io.IOException) {
                // Keep common suggestions when the ingredient list is unavailable.
            } catch (_: retrofit2.HttpException) {
                // Keep common suggestions when the ingredient list is unavailable.
            }
        }
    }

    fun updateInput(value: String) {
        _uiState.update { it.copy(input = value) }
        updateSuggestions()
    }

    private fun updateSuggestions() {
        _uiState.update { state ->
            val suggestions = allSuggestions.filter { suggestion ->
                (state.input.isBlank() || suggestion.contains(state.input.trim(), ignoreCase = true)) &&
                    state.ingredients.none { IngredientMatcher.matches(it, suggestion) }
            }.take(8)
            state.copy(suggestions = suggestions)
        }
    }

    fun addIngredient() {
        val name = _uiState.value.input
        if (name.isBlank()) return
        viewModelScope.launch {
            fridgeRepository.add(name)
            _uiState.update { it.copy(input = "") }
        }
    }

    fun addSuggestion(name: String) {
        viewModelScope.launch {
            fridgeRepository.add(name)
            _uiState.update { it.copy(input = "") }
        }
    }

    fun removeIngredient(name: String) {
        viewModelScope.launch { fridgeRepository.remove(name) }
    }
}
