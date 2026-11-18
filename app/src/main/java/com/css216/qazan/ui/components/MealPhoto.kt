package com.css216.qazan.ui.components

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.css216.qazan.R

@Composable
fun MealPhoto(name: String, thumbUrl: String?, modifier: Modifier = Modifier, large: Boolean = false) {
    if (thumbUrl.isNullOrBlank()) {
        MealPlaceholder(modifier)
        return
    }
    val placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant)
    AsyncImage(
        model = thumbUrl + if (large) "/large" else "/small",
        contentDescription = stringResource(R.string.meal_photo, name),
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant),
        contentScale = ContentScale.Crop,
        placeholder = placeholder,
        error = placeholder
    )
}
