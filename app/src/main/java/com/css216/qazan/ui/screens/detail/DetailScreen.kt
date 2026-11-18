package com.css216.qazan.ui.screens.detail

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.css216.qazan.R
import com.css216.qazan.ui.AppViewModelProvider
import com.css216.qazan.data.fake.FakeRecipeData
import com.css216.qazan.data.model.Ingredient
import com.css216.qazan.ui.components.ErrorView
import com.css216.qazan.ui.components.FavoriteButton
import com.css216.qazan.ui.components.LoadingView
import com.css216.qazan.ui.components.MealPhoto
import com.css216.qazan.ui.components.ScreenHeading
import com.css216.qazan.ui.theme.Dimensions
import com.css216.qazan.ui.theme.QazanTheme

@Composable
fun DetailRoute(
    onBack: () -> Unit,
    onOpenShopping: () -> Unit,
    viewModel: DetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    DetailScreen(uiState, onBack, onOpenShopping, viewModel::loadMeal, viewModel::toggleFavorite, viewModel::selectTab, viewModel::addMissingToShopping)
}

@Composable
fun DetailScreen(
    uiState: DetailUiState,
    onBack: () -> Unit,
    onOpenShopping: () -> Unit,
    onRetry: () -> Unit,
    onFavoriteClick: () -> Unit,
    onTabChange: (DetailTab) -> Unit,
    onAddMissing: () -> Unit
) {
    val content = uiState.content
    if (content !is DetailContent.Success) {
        Column(Modifier.padding(Dimensions.screenPadding)) {
            ScreenHeading(stringResource(R.string.recipe), onBack = onBack)
            when (content) {
                DetailContent.Loading -> LoadingView()
                DetailContent.Error -> ErrorView(onRetry)
                else -> Unit
            }
        }
        return
    }
    Column(Modifier.fillMaxSize()) {
        LazyColumn(Modifier.weight(1f)) {
            item {
                Box(Modifier.fillMaxWidth().height(250.dp)) {
                    MealPhoto(content.meal.name, content.meal.thumbUrl, Modifier.fillMaxSize(), large = true)
                    Row(Modifier.fillMaxWidth().padding(Dimensions.large), horizontalArrangement = Arrangement.SpaceBetween) {
                        Surface(shape = CircleShape) {
                            IconButton(onClick = onBack) { Icon(painterResource(R.drawable.ic_arrow_back), stringResource(R.string.back)) }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(Dimensions.small)) {
                            Surface(shape = CircleShape) { FavoriteButton(content.meal.name, content.isFavorite, onFavoriteClick) }
                            Surface(shape = CircleShape) {
                                IconButton(onClick = onOpenShopping) { Icon(painterResource(R.drawable.ic_shopping_cart), stringResource(R.string.shopping)) }
                            }
                        }
                    }
                }
            }
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth().offset(y = (-26).dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(Modifier.padding(Dimensions.screenPadding), verticalArrangement = Arrangement.spacedBy(Dimensions.large)) {
                        Text(content.meal.name, style = MaterialTheme.typography.headlineMedium)
                        Row(horizontalArrangement = Arrangement.spacedBy(Dimensions.small)) {
                            RecipeTag(content.meal.area)
                            RecipeTag(content.meal.category)
                        }
                        Text(
                            stringResource(R.string.have_count, content.meal.ingredients.count { it.name in content.have }, content.meal.ingredients.size),
                            color = MaterialTheme.colorScheme.tertiary,
                            style = MaterialTheme.typography.labelLarge
                        )
                        SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
                            DetailTab.entries.forEachIndexed { index, tab ->
                                SegmentedButton(
                                    selected = uiState.selectedTab == tab,
                                    onClick = { onTabChange(tab) },
                                    shape = SegmentedButtonDefaults.itemShape(index, DetailTab.entries.size)
                                ) { Text(stringResource(if (tab == DetailTab.Ingredients) R.string.ingredients_tab else R.string.instructions_tab)) }
                            }
                        }
                        if (uiState.selectedTab == DetailTab.Ingredients) {
                            content.meal.ingredients.forEach { ingredient ->
                                IngredientRow(ingredient, ingredient.name in content.have)
                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            }
                        } else Text(content.meal.instructions, style = MaterialTheme.typography.bodyLarge)
                        Text(stringResource(R.string.demo_recipe), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
        Column(
            Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(Dimensions.large),
            verticalArrangement = Arrangement.spacedBy(Dimensions.small)
        ) {
            if (uiState.addedToShopping) Text(stringResource(R.string.shopping_added), style = MaterialTheme.typography.bodySmall)
            Button(
                onClick = if (uiState.addedToShopping) onOpenShopping else onAddMissing,
                enabled = uiState.addedToShopping || content.missing.isNotEmpty(),
                modifier = Modifier.fillMaxWidth().heightIn(min = Dimensions.buttonHeight),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    when {
                        uiState.addedToShopping -> stringResource(R.string.open_shopping)
                        content.missing.isEmpty() -> stringResource(R.string.all_ingredients_available)
                        else -> stringResource(R.string.add_missing, content.missing.size)
                    }
                )
            }
        }
    }
}

@Composable
private fun RecipeTag(text: String) {
    Surface(shape = MaterialTheme.shapes.small, color = MaterialTheme.colorScheme.surfaceVariant) {
        Text(text, Modifier.padding(horizontal = 12.dp, vertical = 8.dp), style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
private fun IngredientRow(ingredient: Ingredient, have: Boolean) {
    val missingDescription = stringResource(R.string.ingredient_missing)
    val outline = MaterialTheme.colorScheme.outline
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Dimensions.medium)) {
        if (have) {
            Surface(shape = CircleShape, color = MaterialTheme.colorScheme.tertiary) {
                Icon(painterResource(R.drawable.ic_check), stringResource(R.string.ingredient_have), Modifier.size(24.dp).padding(3.dp), tint = MaterialTheme.colorScheme.surface)
            }
        } else {
            Canvas(Modifier.size(24.dp).semantics { contentDescription = missingDescription }) {
                drawCircle(outline, radius = size.minDimension / 2 - 1.dp.toPx(), style = Stroke(1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(4.dp.toPx(), 3.dp.toPx()))))
            }
        }
        Text(ingredient.name, Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
        Text(ingredient.measure, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DetailPreview() {
    QazanTheme {
        DetailScreen(DetailUiState(DetailContent.Success(FakeRecipeData.meals.first())), {}, {}, {}, {}, {}, {})
    }
}
