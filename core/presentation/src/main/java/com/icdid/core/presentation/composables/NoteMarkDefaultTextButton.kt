package com.icdid.core.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun NoteMarkDefaultTextButton(
    modifier: Modifier = Modifier,
    text: String,
    uppercase: Boolean = false,
    style: TextStyle = MaterialTheme.typography.titleSmall,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick() }
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = if(uppercase) text.uppercase() else text,
            style = style.copy(
                color = MaterialTheme.colorScheme.primary,
            )
        )
    }
}