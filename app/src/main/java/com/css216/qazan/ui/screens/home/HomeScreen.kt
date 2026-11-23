package com.css216.qazan.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.css216.qazan.R
import com.css216.qazan.data.fake.FakeRecipeData
import com.css216.qazan.data.model.Category
import com.css216.qazan.ui.AppViewModelProvider
import com.css216.qazan.ui.components.EmptyView
import com.css216.qazan.ui.components.ErrorView
import com.css216.qazan.ui.components.LoadingView
import com.css216.qazan.ui.components.MealGridCard
import com.css216.qazan.ui.components.SectionHeader
import com.css216.qazan.ui.components.SuggestionChip
import com.css216.qazan.ui.theme.Dimensions
import com.css216.qazan.ui.theme.QazanTheme

@Composable
fun HomeRoute(
    onOpenFridge: () -> Unit,
    onMealClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onOpenShopping: () -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreen(
        uiState, onOpenFridge, onMealClick, onCategoryClick, onOpenShopping,
        viewModel::loadRecipe, viewModel::toggleFavorite, viewModel::setDarkTheme
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onOpenFridge: () -> Unit,
    onMealClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onOpenShopping: () -> Unit,
    onNextRecipe: () -> Unit,
    onFavoriteClick: () -> Unit,
    onThemeChange: (Boolean) -> Unit
) {
    val isDarkTheme = uiState.darkTheme ?: isSystemInDarkTheme()
    LazyColumn(
        contentPadding = PaddingValues(Dimensions.screenPadding),
        verticalArrangement = Arrangement.spacedBy(Dimensions.large)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painterResource(R.drawable.ic_qazan), null, Modifier.size(48.dp).clip(MaterialTheme.shapes.small))
                Column(Modifier.weight(1f).padding(start = Dimensions.medium)) {
                    Text(stringResource(R.string.app_name), style = MaterialTheme.typography.headlineSmall)
                    Text(stringResource(R.string.slogan), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                }
                TextButton(
                    onClick = { onThemeChange(!isDarkTheme) },
                    modifier = Modifier.heightIn(min = Dimensions.touchTarget)
                ) {
                    Text(stringResource(if (isDarkTheme) R.string.dark_theme else R.string.light_theme))
                }
                IconButton(onClick = onOpenShopping) {
                    Icon(painterResource(R.drawable.ic_shopping_cart), stringResource(R.string.shopping))
                }
            }
        }
        item { Text(stringResource(R.string.home_title), style = MaterialTheme.typography.displaySmall) }
        item {
            Card(
                onClick = onOpenFridge,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Row(Modifier.padding(Dimensions.screenPadding), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(stringResource(R.string.fridge_prompt), style = MaterialTheme.typography.titleLarge)
                        Text(
                            if (uiState.fridgeCount == 0) stringResource(R.string.fridge_prompt_empty)
                            else stringResource(R.string.fridge_product_count, uiState.fridgeCount),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Icon(painterResource(R.drawable.ic_kitchen), null, Modifier.padding(start = 12.dp).size(40.dp))
                }
            }
        }
        when (val content = uiState.content) {
            HomeContent.Loading -> item { LoadingView() }
            HomeContent.Error -> item { ErrorView(onNextRecipe) }
            is HomeContent.Success -> {
                item { SectionHeader(stringResource(R.string.recipe_of_day), stringResource(R.string.another_recipe), onNextRecipe) }
                item {
                    MealGridCard(
                        content.meal.toSummary(),
                        onClick = { onMealClick(content.meal.id) },
                        onFavoriteClick = onFavoriteClick,
                        isFavorite = content.meal.id in uiState.favoriteIds
                    )
                }
                item { SectionHeader(stringResource(R.string.categories)) }
                item {
                    if (content.categories.isEmpty()) EmptyView(stringResource(R.string.no_categories))
                    else LazyRow(horizontalArrangement = Arrangement.spacedBy(Dimensions.small)) {
                        items(content.categories, key = { it.name }) { category ->
                            SuggestionChip(category.name, { onCategoryClick(category.name) })
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomePreview() {
    QazanTheme {
        HomeScreen(
            HomeUiState(HomeContent.Success(FakeRecipeData.meals.first(), listOf(Category("Chicken", null)))),
            {}, {}, {}, {}, {}, {}, {}
        )
    }
}
