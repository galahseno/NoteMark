package com.icdid.auth.presentation.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.icdid.auth.presentation.R
import com.icdid.auth.presentation.landing.composables.LandingButtons
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.MobilePortrait
import com.icdid.core.presentation.utils.TabletPortrait
import com.icdid.core.presentation.utils.applyIf

@Composable
fun LandingPortraitView(
    modifier: Modifier = Modifier,
    onAction: (LandingAction) -> Unit,
    isTablet: Boolean = false,
) {
    Scaffold { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    bottom = paddingValues
                        .calculateBottomPadding()
                ),
        ) {
            Image(
                painter = painterResource(id = if (isTablet) R.drawable.landing_tablet else R.drawable.landing_phone),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = if (isTablet) ContentScale.FillBounds else ContentScale.Fit,
                alignment = Alignment.TopCenter
            )
            Column(
                modifier = Modifier
                    .applyIf(isTablet) {
                        padding(horizontal = 60.dp)
                    }
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = if (isTablet) 24.dp else 20.dp,
                            topEnd = if (isTablet) 24.dp else 20.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                    .then(
                        if (isTablet) {
                            Modifier.padding(48.dp)
                        } else {
                            Modifier
                                .padding(
                                    top = 24.dp,
                                    end = 16.dp,
                                    start = 16.dp,
                                )
                        }
                    )
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.landing_title),
                    style = if (isTablet) {
                        LocalNoteMarkTypography.current.titleXLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        LocalNoteMarkTypography.current.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Start
                        )
                    }
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.landing_subtitle),
                    style = LocalNoteMarkTypography.current.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = if (isTablet) TextAlign.Center else TextAlign.Start
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
                LandingButtons(
                    onAction = onAction,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@MobilePortrait
@Composable
private fun LandingScreenPreview() {
    NoteMarkTheme {
        LandingPortraitView(
            onAction = {}
        )
    }
}

@TabletPortrait
@Composable
private fun LandingScreenTabletPreview() {
    NoteMarkTheme {
        LandingPortraitView(
            onAction = {},
            isTablet = true
        )
    }
}