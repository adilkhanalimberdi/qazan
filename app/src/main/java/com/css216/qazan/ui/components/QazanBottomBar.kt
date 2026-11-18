package com.css216.qazan.ui.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.css216.qazan.R
import com.css216.qazan.ui.navigation.QazanDestination
import com.css216.qazan.ui.theme.Dimensions

private data class BottomTab(val destination: QazanDestination, val label: Int, val icon: Int)

private val tabs = listOf(
    BottomTab(QazanDestination.Home, R.string.home, R.drawable.ic_home),
    BottomTab(QazanDestination.Fridge, R.string.fridge, R.drawable.ic_kitchen),
    BottomTab(QazanDestination.Search, R.string.search, R.drawable.ic_search),
    BottomTab(QazanDestination.Favorites, R.string.favorites, R.drawable.ic_favorite_border)
)

@Composable
fun QazanBottomBar(currentRoute: String?, onNavigate: (QazanDestination) -> Unit) {
    NavigationBar(
        modifier = Modifier.heightIn(min = Dimensions.bottomBarHeight),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        tabs.forEach { tab ->
            NavigationBarItem(
                selected = currentRoute == tab.destination.route,
                onClick = { onNavigate(tab.destination) },
                icon = { Icon(painterResource(tab.icon), null) },
                label = { Text(stringResource(tab.label), style = MaterialTheme.typography.labelMedium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}
