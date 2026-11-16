package com.css216.qazan.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.css216.qazan.ui.components.QazanBottomBar
import com.css216.qazan.ui.navigation.QazanDestination
import com.css216.qazan.ui.navigation.QazanNavHost

@Composable
fun QazanApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
    val showBottomBar = route != QazanDestination.Detail.route && route != QazanDestination.Shopping.route

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                QazanBottomBar(route) { destination ->
                    if (destination == QazanDestination.Home) {
                        navController.popBackStack(QazanDestination.Home.route, inclusive = false, saveState = true)
                        return@QazanBottomBar
                    }
                    val target = if (destination == QazanDestination.Search) {
                        QazanDestination.Search.createRoute()
                    } else destination.route
                    navController.navigate(target) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    ) { innerPadding ->
        QazanNavHost(navController, Modifier.fillMaxSize().padding(innerPadding))
    }
}
