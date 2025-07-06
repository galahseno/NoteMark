package com.icdid.core.presentation.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
fun FadeVisibilityComponent(
    isVisible: Boolean,
    content: @Composable () -> Unit,
) {
    val alpha by animateFloatAsState(
        targetValue = if(isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 800)
    )

    Box(
        modifier = Modifier
            .alpha(alpha)
    ) {
        content()
    }
}