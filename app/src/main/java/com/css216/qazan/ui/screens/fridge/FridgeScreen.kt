package com.css216.qazan.ui.screens.fridge

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.css216.qazan.R
import com.css216.qazan.ui.AppViewModelProvider
import com.css216.qazan.ui.components.EmptyView
import com.css216.qazan.ui.components.IngredientChip
import com.css216.qazan.ui.components.ScreenHeading
import com.css216.qazan.ui.components.SectionHeader
import com.css216.qazan.ui.components.SuggestionChip
import com.css216.qazan.ui.theme.Dimensions
import com.css216.qazan.ui.theme.QazanTheme

@Composable
fun FridgeRoute(onFindRecipes: () -> Unit, viewModel: FridgeViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val uiState by viewModel.uiState.collectAsState()
    FridgeScreen(uiState, viewModel::updateInput, viewModel::addIngredient, viewModel::addSuggestion, viewModel::removeIngredient, onFindRecipes)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FridgeScreen(
    uiState: FridgeUiState,
    onInputChange: (String) -> Unit,
    onAdd: () -> Unit,
    onSuggestionClick: (String) -> Unit,
    onRemove: (String) -> Unit,
    onFindRecipes: () -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current
    LazyColumn(
        modifier = Modifier.fillMaxSize().imePadding(),
        contentPadding = PaddingValues(Dimensions.screenPadding),
        verticalArrangement = Arrangement.spacedBy(Dimensions.large)
    ) {
        item { ScreenHeading(stringResource(R.string.fridge), stringResource(R.string.fridge_subtitle)) }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(Dimensions.small), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = uiState.input,
                    onValueChange = onInputChange,
                    label = { Text(stringResource(R.string.ingredient_input)) },
                    placeholder = { Text(stringResource(R.string.ingredient_hint)) },
                    modifier = Modifier.weight(1f).heightIn(min = Dimensions.inputHeight),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { onAdd(); keyboard?.hide() })
                )
                FilledIconButton(onClick = onAdd, enabled = uiState.input.isNotBlank(), modifier = Modifier.size(Dimensions.buttonHeight)) {
                    Icon(painterResource(R.drawable.ic_add), stringResource(R.string.add_ingredient))
                }
            }
        }
        item {
            Text(stringResource(R.string.ingredient_language_hint), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        item { SectionHeader(stringResource(R.string.your_ingredients, uiState.ingredients.size)) }
        item {
            if (uiState.ingredients.isEmpty()) EmptyView(stringResource(R.string.fridge_empty))
            else FlowRow(horizontalArrangement = Arrangement.spacedBy(Dimensions.small)) {
                uiState.ingredients.forEach { ingredient -> IngredientChip(ingredient, { onRemove(ingredient) }) }
            }
        }
        if (uiState.suggestions.isNotEmpty()) {
            item { SectionHeader(stringResource(if (uiState.input.isBlank()) R.string.common_ingredients else R.string.ingredient_suggestions)) }
            item {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(Dimensions.small)) {
                    uiState.suggestions.forEach { ingredient -> SuggestionChip(ingredient, { onSuggestionClick(ingredient) }) }
                }
            }
        }
        item {
            Button(
                onClick = { keyboard?.hide(); onFindRecipes() },
                enabled = uiState.ingredients.isNotEmpty(),
                modifier = Modifier.fillMaxWidth().heightIn(min = Dimensions.buttonHeight),
                shape = MaterialTheme.shapes.large
            ) { Text(stringResource(R.string.find_recipes)) }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FridgePreview() {
    QazanTheme {
        FridgeScreen(FridgeUiState(ingredients = listOf("Chicken", "Rice"), suggestions = listOf("Tomato", "Egg", "Milk")), {}, {}, {}, {}, {})
    }
}
