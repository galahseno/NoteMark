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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.icdid.auth.presentation.R
import com.icdid.core.presentation.composables.NoteMarkOutlinedButton
import com.icdid.core.presentation.composables.NoteMarkPrimaryButton
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.theme.landingLandscape
import utils.MobileLandscape
import utils.TabletLandscape

@Composable
fun LandingLandscapeView(
    onAction: (LandingAction) -> Unit,
    modifier: Modifier = Modifier,
    isTablet: Boolean = false
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(landingLandscape),
    ) {
        Image(
            painter = painterResource(id = R.drawable.landing_landscape),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(20.dp),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .padding(vertical = if (isTablet) 220.dp else 30.dp)
                .fillMaxHeight()
                .fillMaxWidth(0.52f)
                .clip(
                    RoundedCornerShape(
                        topStart = 20.dp,
                        bottomStart = 20.dp
                    )
                )
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                .padding(start = 40.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
                .align(Alignment.CenterEnd),
            verticalArrangement = Arrangement.spacedBy(
                12.dp,
                Alignment.CenterVertically
            ),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.landing_title),
                style = if (isTablet) {
                    LocalNoteMarkTypography.current.titleXLarge
                } else {
                    LocalNoteMarkTypography.current.titleLarge
                }
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.landing_subtitle),
                style = LocalNoteMarkTypography.current.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            NoteMarkPrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp),
                text = stringResource(R.string.landing_button_text),
                onClick = {
                    onAction(LandingAction.OnGettingStartedClicked)
                },
                color = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
                enabled = true
            )
            NoteMarkOutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp),
                text = stringResource(R.string.login_button_text),
                onClick = {
                    onAction(LandingAction.OnLoginClicked)
                },
                borderColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@MobileLandscape
@Composable
private fun LandingScreenPreview() {
    NoteMarkTheme {
        LandingLandscapeView(
            onAction = {}
        )
    }
}

@TabletLandscape
@Composable
private fun LandingScreenTabletPreview() {
    NoteMarkTheme {
        LandingLandscapeView(
            onAction = {},
            isTablet = true
        )
    }
}