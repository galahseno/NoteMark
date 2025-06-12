package com.icdid.core.presentation.composables

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun NoteMarkDefaultScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary
    ) { innerPadding ->

        val view = LocalView.current
        val paddingTop = innerPadding.calculateTopPadding() + 8.dp

        Box(
            modifier = modifier
                .padding(top = paddingTop)
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp
                    )
                )
        ) {
            content()
        }

        SideEffect {
            val window = (view.context as? Activity)?.window
            if(!view.isInEditMode && window != null) {
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
            }
        }
    }
}