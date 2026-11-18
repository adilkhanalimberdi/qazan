package com.css216.qazan.ui.screens.favorites

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.css216.qazan.R
import com.css216.qazan.data.fake.FakeRecipeData
import com.css216.qazan.data.model.MealSummary
import com.css216.qazan.ui.AppViewModelProvider
import com.css216.qazan.ui.components.EmptyView
import com.css216.qazan.ui.components.MealGridCard
import com.css216.qazan.ui.components.ScreenHeading
import com.css216.qazan.ui.theme.Dimensions
import com.css216.qazan.ui.theme.QazanTheme

@Composable
fun FavoritesRoute(
    onMealClick: (String) -> Unit,
    onOpenShopping: () -> Unit,
    viewModel: FavoritesViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    FavoritesScreen(uiState, onMealClick, onOpenShopping, viewModel::toggleFavorite)
}

@Composable
fun FavoritesScreen(uiState: FavoritesUiState, onMealClick: (String) -> Unit, onOpenShopping: () -> Unit, onFavoriteClick: (MealSummary) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(Dimensions.screenPadding),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.medium),
        verticalArrangement = Arrangement.spacedBy(Dimensions.large)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            ScreenHeading(stringResource(R.string.favorites), stringResource(R.string.favorites_count, uiState.meals.size)) {
                IconButton(onClick = onOpenShopping) {
                    BadgedBox(badge = { if (uiState.shoppingCount > 0) Badge { Text(uiState.shoppingCount.toString()) } }) {
                        Icon(painterResource(R.drawable.ic_shopping_cart), stringResource(R.string.shopping_remaining, uiState.shoppingCount))
                    }
                }
            }
        }
        if (uiState.meals.isEmpty()) item(span = { GridItemSpan(maxLineSpan) }) {
            EmptyView(stringResource(R.string.favorites_empty))
        }
        items(uiState.meals, key = { it.id }) { meal ->
            MealGridCard(meal, { onMealClick(meal.id) }, { onFavoriteClick(meal) }, isFavorite = true)
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FavoritesPreview() {
    QazanTheme { FavoritesScreen(FavoritesUiState(FakeRecipeData.meals.take(2).map { it.toSummary() }, 4), {}, {}, {}) }
}
