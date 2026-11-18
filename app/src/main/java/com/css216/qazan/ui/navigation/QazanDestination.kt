package com.css216.qazan.ui.navigation

import android.net.Uri

sealed class QazanDestination(val route: String) {
    data object Home : QazanDestination("home")
    data object Fridge : QazanDestination("fridge")
    data object Search : QazanDestination("search?category={category}") {
        const val ARG = "category"
        fun createRoute(category: String = "") = "search?category=${Uri.encode(category)}"
    }
    data object Favorites : QazanDestination("favorites")
    data object Shopping : QazanDestination("shopping")
    data object Detail : QazanDestination("detail/{mealId}") {
        const val ARG = "mealId"
        fun createRoute(mealId: String) = "detail/${Uri.encode(mealId)}"
    }
}
