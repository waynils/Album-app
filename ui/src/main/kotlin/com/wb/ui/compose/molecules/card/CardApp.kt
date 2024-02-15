package com.wb.ui.compose.molecules.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wb.ui.utils.applyIfNotNull

private val cardRadius = 10.dp

@Composable
fun CardApp(
    onCardAction: (() -> Unit)? = null,
    modifier: Modifier,
    content: @Composable () -> Unit
) {

    Card(
        modifier = modifier.applyIfNotNull(onCardAction) {
            clickable(onClick = it)
        },
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(cardRadius)
    ) {

        content()
    }
}