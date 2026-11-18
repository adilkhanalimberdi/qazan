package com.css216.qazan.ui.screens.fridge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.css216.qazan.data.model.IngredientMatcher
import com.css216.qazan.data.repository.FridgeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FridgeViewModel(private val fridgeRepository: FridgeRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(FridgeUiState())
    val uiState: StateFlow<FridgeUiState> = _uiState.asStateFlow()
    private val commonIngredients = listOf("Chicken", "Rice", "Tomato", "Egg", "Onion", "Potato", "Cheese", "Milk")

    init {
        viewModelScope.launch {
            fridgeRepository.ingredients.collect { ingredients ->
                val suggestions = commonIngredients.filter { suggestion ->
                    ingredients.none { IngredientMatcher.matches(it, suggestion) }
                }
                _uiState.update { it.copy(ingredients = ingredients, suggestions = suggestions) }
            }
        }
    }

    fun updateInput(value: String) {
        _uiState.update { it.copy(input = value) }
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
        viewModelScope.launch { fridgeRepository.add(name) }
    }

    fun removeIngredient(name: String) {
        viewModelScope.launch { fridgeRepository.remove(name) }
    }
}
