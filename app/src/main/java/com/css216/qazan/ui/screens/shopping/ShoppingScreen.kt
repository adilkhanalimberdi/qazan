package com.css216.qazan.ui.screens.shopping

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.css216.qazan.R
import com.css216.qazan.data.model.ShoppingItem
import com.css216.qazan.ui.AppViewModelProvider
import com.css216.qazan.ui.components.EmptyView
import com.css216.qazan.ui.components.ScreenHeading
import com.css216.qazan.ui.components.SectionHeader
import com.css216.qazan.ui.theme.Dimensions
import com.css216.qazan.ui.theme.QazanTheme

@Composable
fun ShoppingRoute(onBack: () -> Unit, viewModel: ShoppingViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val uiState by viewModel.uiState.collectAsState()
    ShoppingScreen(uiState, onBack, viewModel::setBought, viewModel::clearBought)
}

@Composable
fun ShoppingScreen(uiState: ShoppingUiState, onBack: () -> Unit, onBoughtChange: (Long, Boolean) -> Unit, onClearBought: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(Dimensions.screenPadding),
            verticalArrangement = Arrangement.spacedBy(Dimensions.large)
        ) {
            item { ScreenHeading(stringResource(R.string.shopping_title), onBack = onBack) }
            if (uiState.items.isEmpty()) item { EmptyView(stringResource(R.string.shopping_empty)) }
            else {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(Dimensions.small)) {
                        Text(stringResource(R.string.shopping_progress, uiState.boughtCount, uiState.items.size), style = MaterialTheme.typography.bodyMedium)
                        LinearProgressIndicator(progress = { uiState.boughtCount.toFloat() / uiState.items.size }, modifier = Modifier.fillMaxWidth())
                    }
                }
                uiState.items.groupBy { it.source }.forEach { (source, items) ->
                    item { SectionHeader(source ?: stringResource(R.string.other_products)) }
                    items(items, key = { it.id }) { item ->
                        ShoppingRow(item, { onBoughtChange(item.id, it) })
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    }
                }
            }
        }
        Button(
            onClick = onClearBought,
            enabled = uiState.boughtCount > 0,
            modifier = Modifier.padding(Dimensions.screenPadding).fillMaxWidth().heightIn(min = Dimensions.buttonHeight),
            shape = MaterialTheme.shapes.large
        ) { Text(stringResource(R.string.clear_bought)) }
    }
}

@Composable
private fun ShoppingRow(item: ShoppingItem, onBoughtChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().heightIn(min = Dimensions.buttonHeight)
            .toggleable(value = item.isBought, role = Role.Checkbox, onValueChange = onBoughtChange)
            .padding(vertical = Dimensions.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimensions.medium)
    ) {
        Checkbox(checked = item.isBought, onCheckedChange = null)
        Text(
            item.name,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            textDecoration = if (item.isBought) TextDecoration.LineThrough else TextDecoration.None,
            color = if (item.isBought) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
        )
        Text(item.measure, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ShoppingPreview() {
    QazanTheme {
        ShoppingScreen(ShoppingUiState(listOf(ShoppingItem(1, "Tomato", "2", "Chicken & Rice"), ShoppingItem(2, "Garlic", "2 cloves", "Chicken & Rice", true))), {}, { _, _ -> }, {})
    }
}
