package com.css216.qazan.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.css216.qazan.R
import com.css216.qazan.ui.components.ScreenHeading
import com.css216.qazan.ui.theme.Dimensions

@Composable
fun QazanNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = QazanDestination.Home.route, modifier = modifier) {
        composable(QazanDestination.Home.route) { PlaceholderScreen(stringResource(R.string.home)) }
        composable(QazanDestination.Fridge.route) { PlaceholderScreen(stringResource(R.string.fridge)) }
        composable(
            QazanDestination.Search.route,
            arguments = listOf(navArgument(QazanDestination.Search.ARG) { defaultValue = "" })
        ) { PlaceholderScreen(stringResource(R.string.search)) }
        composable(QazanDestination.Favorites.route) { PlaceholderScreen(stringResource(R.string.favorites)) }
        composable(QazanDestination.Shopping.route) {
            PlaceholderScreen(stringResource(R.string.shopping), onBack = { navController.navigateUp() })
        }
        composable(
            QazanDestination.Detail.route,
            arguments = listOf(navArgument(QazanDestination.Detail.ARG) { type = NavType.StringType })
        ) {
            PlaceholderScreen(stringResource(R.string.recipe), onBack = { navController.navigateUp() })
        }
    }
}

@Composable
private fun PlaceholderScreen(title: String, onBack: (() -> Unit)? = null) {
    Column(Modifier.padding(Dimensions.screenPadding)) {
        ScreenHeading(title, onBack = onBack)
        Text(stringResource(R.string.screen_in_progress))
    }
}
