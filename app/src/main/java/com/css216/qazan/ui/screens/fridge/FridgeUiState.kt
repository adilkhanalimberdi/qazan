package com.css216.qazan.ui.screens.fridge

data class FridgeUiState(
    val input: String = "",
    val ingredients: List<String> = emptyList(),
    val suggestions: List<String> = emptyList()
)
