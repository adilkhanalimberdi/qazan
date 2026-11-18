package com.css216.qazan.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.css216.qazan.ui.screens.home.HomeRoute
import com.css216.qazan.ui.screens.fridge.FridgeRoute
import com.css216.qazan.ui.screens.search.SearchRoute
import com.css216.qazan.ui.screens.detail.DetailRoute
import com.css216.qazan.ui.screens.favorites.FavoritesRoute
import com.css216.qazan.ui.screens.shopping.ShoppingRoute

@Composable
fun QazanNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = QazanDestination.Home.route, modifier = modifier) {
        composable(QazanDestination.Home.route) {
            HomeRoute(
                onOpenFridge = { navController.navigate(QazanDestination.Fridge.route) { launchSingleTop = true } },
                onMealClick = { navController.navigate(QazanDestination.Detail.createRoute(it)) },
                onCategoryClick = { category ->
                    navController.navigate(QazanDestination.Search.createRoute(category)) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                    }
                },
                onOpenShopping = { navController.navigate(QazanDestination.Shopping.route) }
            )
        }
        composable(QazanDestination.Fridge.route) {
            FridgeRoute(onFindRecipes = {
                navController.navigate(QazanDestination.Search.createRoute()) {
                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                    launchSingleTop = true
                }
            })
        }
        composable(
            QazanDestination.Search.route,
            arguments = listOf(navArgument(QazanDestination.Search.ARG) { defaultValue = "" })
        ) {
            SearchRoute(
                onMealClick = { navController.navigate(QazanDestination.Detail.createRoute(it)) },
                onOpenFridge = {
                    navController.navigate(QazanDestination.Fridge.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable(QazanDestination.Favorites.route) {
            FavoritesRoute(
                onMealClick = { navController.navigate(QazanDestination.Detail.createRoute(it)) },
                onOpenShopping = { navController.navigate(QazanDestination.Shopping.route) }
            )
        }
        composable(QazanDestination.Shopping.route) {
            ShoppingRoute(onBack = { navController.navigateUp() })
        }
        composable(
            QazanDestination.Detail.route,
            arguments = listOf(navArgument(QazanDestination.Detail.ARG) { type = NavType.StringType })
        ) {
            DetailRoute(
                onBack = { navController.navigateUp() },
                onOpenShopping = { navController.navigate(QazanDestination.Shopping.route) }
            )
        }
    }
}
