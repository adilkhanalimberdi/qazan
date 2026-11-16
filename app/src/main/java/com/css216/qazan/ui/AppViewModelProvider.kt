package com.css216.qazan.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.css216.qazan.QazanApplication
import com.css216.qazan.ui.screens.home.HomeViewModel
import com.css216.qazan.ui.screens.fridge.FridgeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer { FridgeViewModel(qazanApplication().container.fridgeRepository) }
        initializer {
            val container = qazanApplication().container
            HomeViewModel(container.recipeRepository, container.fridgeRepository, container.favoritesRepository)
        }
    }
}

fun CreationExtras.qazanApplication(): QazanApplication =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as QazanApplication
