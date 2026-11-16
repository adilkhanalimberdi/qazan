package com.css216.qazan.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.css216.qazan.QazanApplication
import com.css216.qazan.ui.screens.home.HomeViewModel
import com.css216.qazan.ui.screens.fridge.FridgeViewModel
import com.css216.qazan.ui.screens.search.SearchViewModel
import com.css216.qazan.ui.screens.detail.DetailViewModel
import com.css216.qazan.ui.screens.favorites.FavoritesViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val container = qazanApplication().container
            FavoritesViewModel(container.favoritesRepository, container.shoppingRepository)
        }
        initializer {
            val container = qazanApplication().container
            DetailViewModel(createSavedStateHandle(), container.recipeRepository, container.favoritesRepository, container.fridgeRepository, container.shoppingRepository)
        }
        initializer {
            val container = qazanApplication().container
            SearchViewModel(createSavedStateHandle(), container.recipeRepository, container.fridgeRepository, container.favoritesRepository)
        }
        initializer { FridgeViewModel(qazanApplication().container.fridgeRepository) }
        initializer {
            val container = qazanApplication().container
            HomeViewModel(container.recipeRepository, container.fridgeRepository, container.favoritesRepository)
        }
    }
}

fun CreationExtras.qazanApplication(): QazanApplication =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as QazanApplication
