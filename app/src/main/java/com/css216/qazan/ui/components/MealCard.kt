package com.css216.qazan.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.css216.qazan.R
import com.css216.qazan.data.model.MealSummary
import com.css216.qazan.ui.theme.Dimensions
import com.css216.qazan.ui.theme.QazanTheme

@Composable
fun MealListCard(
    meal: MealSummary,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    matchedCount: Int? = null,
    selectedCount: Int = 0
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(Modifier.padding(Dimensions.medium), verticalAlignment = Alignment.CenterVertically) {
            MealPhoto(meal.name, meal.thumbUrl, Modifier.size(84.dp).clip(MaterialTheme.shapes.small))
            Column(
                Modifier.weight(1f).padding(start = Dimensions.medium),
                verticalArrangement = Arrangement.spacedBy(Dimensions.extraSmall)
            ) {
                MealDescription(meal)
                if (matchedCount != null) {
                    Surface(color = MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.small) {
                        Text(
                            stringResource(R.string.match_count, matchedCount, selectedCount),
                            Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
            FavoriteButton(meal.name, isFavorite, onFavoriteClick)
        }
    }
}

@Composable
fun MealGridCard(
    meal: MealSummary,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Box {
            MealPhoto(meal.name, meal.thumbUrl, Modifier.fillMaxWidth().aspectRatio(1.45f))
            Surface(
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
            ) {
                FavoriteButton(meal.name, isFavorite, onFavoriteClick)
            }
        }
        Column(Modifier.padding(Dimensions.medium), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            MealDescription(meal)
        }
    }
}

@Composable
private fun MealDescription(meal: MealSummary) {
    Text(meal.name, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
    Text(
        stringResource(R.string.meal_meta, meal.area, meal.category),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MealCardsPreview() {
    QazanTheme {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            val meal = MealSummary("1001", "Chicken & Rice", null, "Chicken", "Indian")
            MealListCard(meal, {}, {}, matchedCount = 3, selectedCount = 5)
            MealGridCard(meal, {}, {})
        }
    }
}
