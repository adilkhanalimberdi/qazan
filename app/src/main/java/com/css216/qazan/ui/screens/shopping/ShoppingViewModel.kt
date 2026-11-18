package com.css216.qazan.ui.screens.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.css216.qazan.data.repository.ShoppingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShoppingViewModel(private val shoppingRepository: ShoppingRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingUiState())
    val uiState: StateFlow<ShoppingUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            shoppingRepository.items.collect { items -> _uiState.update { it.copy(items = items) } }
        }
    }

    fun setBought(id: Long, bought: Boolean) {
        viewModelScope.launch { shoppingRepository.setBought(id, bought) }
    }

    fun clearBought() {
        viewModelScope.launch { shoppingRepository.clearBought() }
    }
}
