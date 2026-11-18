package com.css216.qazan.ui.screens.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.css216.qazan.R
import com.css216.qazan.data.fake.FakeRecipeData
import com.css216.qazan.data.model.MatchedMeal
import com.css216.qazan.data.model.MealSummary
import com.css216.qazan.ui.AppViewModelProvider
import com.css216.qazan.ui.components.EmptyView
import com.css216.qazan.ui.components.ErrorView
import com.css216.qazan.ui.components.LoadingView
import com.css216.qazan.ui.components.MealListCard
import com.css216.qazan.ui.components.ScreenHeading
import com.css216.qazan.ui.theme.Dimensions
import com.css216.qazan.ui.theme.QazanTheme

@Composable
fun SearchRoute(
    onMealClick: (String) -> Unit,
    onOpenFridge: () -> Unit,
    viewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    SearchScreen(uiState, viewModel::updateQuery, viewModel::toggleFridge, viewModel::clearCategory, viewModel::loadResults, viewModel::toggleFavorite, onMealClick, onOpenFridge)
}

@Composable
fun SearchScreen(
    uiState: SearchUiState,
    onQueryChange: (String) -> Unit,
    onToggleFridge: () -> Unit,
    onClearCategory: () -> Unit,
    onRetry: () -> Unit,
    onFavoriteClick: (MealSummary) -> Unit,
    onMealClick: (String) -> Unit,
    onOpenFridge: () -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current
    LazyColumn(
        modifier = Modifier.fillMaxSize().imePadding(),
        contentPadding = PaddingValues(Dimensions.screenPadding),
        verticalArrangement = Arrangement.spacedBy(Dimensions.medium)
    ) {
        item { ScreenHeading(stringResource(R.string.search_title)) }
        item {
            OutlinedTextField(
                uiState.query, onQueryChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.search_input)) },
                placeholder = { Text(stringResource(R.string.search_hint)) },
                leadingIcon = { Icon(painterResource(R.drawable.ic_search), null) },
                trailingIcon = {
                    if (uiState.query.isNotEmpty()) IconButton(onClick = { onQueryChange("") }) {
                        Icon(painterResource(R.drawable.ic_close), stringResource(R.string.clear_search))
                    }
                },
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { keyboard?.hide() })
            )
        }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(Dimensions.small)) {
                item {
                    FilterChip(
                        uiState.useFridge, onToggleFridge,
                        label = { Text(stringResource(R.string.fridge_filter, uiState.fridgeCount)) },
                        modifier = Modifier.heightIn(min = Dimensions.touchTarget)
                    )
                }
                if (uiState.category.isNotBlank()) item {
                    InputChip(
                        selected = true,
                        onClick = onClearCategory,
                        label = { Text(uiState.category) },
                        trailingIcon = { Icon(painterResource(R.drawable.ic_close), stringResource(R.string.remove_category, uiState.category)) },
                        modifier = Modifier.heightIn(min = Dimensions.touchTarget)
                    )
                }
            }
        }
        when (val content = uiState.content) {
            SearchContent.Loading -> item { LoadingView() }
            SearchContent.Error -> item { ErrorView(onRetry) }
            is SearchContent.Success -> {
                item { Text(stringResource(R.string.results_count, content.results.size), style = MaterialTheme.typography.bodyMedium) }
                if (content.results.isEmpty()) {
                    item {
                        EmptyView(stringResource(if (uiState.useFridge && uiState.fridgeCount == 0) R.string.search_fridge_empty else R.string.empty_results))
                    }
                    if (uiState.useFridge && uiState.fridgeCount == 0) item {
                        TextButton(onClick = onOpenFridge) {
                            Text(stringResource(R.string.open_fridge), color = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }
                items(content.results, key = { it.meal.id }) { result ->
                    MealListCard(
                        result.meal,
                        onClick = { keyboard?.hide(); onMealClick(result.meal.id) },
                        onFavoriteClick = { onFavoriteClick(result.meal) },
                        isFavorite = result.meal.id in uiState.favoriteIds,
                        matchedCount = if (uiState.useFridge) result.matchedCount else null,
                        selectedCount = result.selectedCount
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SearchPreview() {
    QazanTheme {
        SearchScreen(
            SearchUiState(fridgeCount = 2, content = SearchContent.Success(listOf(MatchedMeal(FakeRecipeData.meals.first().toSummary(), 2, 2)))),
            {}, {}, {}, {}, {}, {}, {}
        )
    }
}
