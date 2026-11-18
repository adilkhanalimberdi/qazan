package com.css216.qazan.ui.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.css216.qazan.R
import com.css216.qazan.ui.theme.Dimensions
import com.css216.qazan.ui.theme.PillShape

@Composable
fun IngredientChip(name: String, onRemove: () -> Unit, modifier: Modifier = Modifier) {
    val description = stringResource(R.string.remove_ingredient, name)
    InputChip(
        selected = true,
        onClick = onRemove,
        label = { Text(name) },
        trailingIcon = { Icon(painterResource(R.drawable.ic_close), null, Modifier.size(18.dp)) },
        modifier = modifier.heightIn(min = Dimensions.touchTarget).semantics { contentDescription = description },
        shape = PillShape,
        colors = InputChipDefaults.inputChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
            selectedTrailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        border = null
    )
}

@Composable
fun SuggestionChip(name: String, onAdd: () -> Unit, modifier: Modifier = Modifier) {
    AssistChip(
        onClick = onAdd,
        label = { Text(name) },
        modifier = modifier.heightIn(min = Dimensions.touchTarget),
        shape = PillShape
    )
}
