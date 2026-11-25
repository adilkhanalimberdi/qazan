package com.css216.qazan.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.css216.qazan.R
import com.css216.qazan.data.model.Meal
import com.css216.qazan.ui.theme.Dimensions

sealed interface MealPreviewState {
    data object Loading : MealPreviewState
    data class Success(val meal: Meal) : MealPreviewState
    data object Error : MealPreviewState
}

@Composable
fun MealPreviewPane(
    state: MealPreviewState?,
    onRetry: () -> Unit,
    onOpenMeal: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxHeight().padding(Dimensions.medium),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        when (state) {
            null -> EmptyView(stringResource(R.string.select_meal))
            MealPreviewState.Loading -> LoadingView()
            MealPreviewState.Error -> ErrorView(onRetry)
            is MealPreviewState.Success -> {
                val meal = state.meal
                Column(
                    Modifier.fillMaxHeight().verticalScroll(rememberScrollState()).padding(Dimensions.large),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.medium)
                ) {
                    MealPhoto(meal.name, meal.thumbUrl, Modifier.fillMaxWidth().height(180.dp))
                    Text(meal.name, style = MaterialTheme.typography.headlineMedium)
                    Text(meal.area + " · " + meal.category, style = MaterialTheme.typography.bodyMedium)
                    Text(meal.ingredients.joinToString { it.name }, style = MaterialTheme.typography.bodyMedium)
                    Text(meal.instructions, style = MaterialTheme.typography.bodyMedium, maxLines = 8)
                    Button(onClick = { onOpenMeal(meal.id) }, modifier = Modifier.fillMaxWidth()) {
                        Text(stringResource(R.string.open_recipe))
                    }
                }
            }
        }
    }
}
