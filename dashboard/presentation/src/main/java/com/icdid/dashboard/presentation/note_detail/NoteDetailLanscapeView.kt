package com.icdid.dashboard.presentation.note_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icdid.core.presentation.composables.FadeVisibilityComponent
import com.icdid.core.presentation.composables.NoteMarkDefaultTextButton
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.MobileLandscape
import com.icdid.core.presentation.utils.TabletLandscape
import com.icdid.core.presentation.utils.UiText
import com.icdid.dashboard.presentation.R
import com.icdid.dashboard.presentation.note_detail.composables.NavigationButton
import com.icdid.dashboard.presentation.note_detail.composables.NoteEditMode
import com.icdid.dashboard.presentation.note_detail.composables.NoteViewMode
import com.icdid.dashboard.presentation.note_detail.model.NoteDetailMode
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun NoteDetailLandscapeView(
    isTablet: Boolean = false,
    state: NoteDetailState = NoteDetailState(),
    onAction: (NoteDetailAction) -> Unit = {},
) {
    val spaceArrangement = when (state.noteMode) {
        NoteDetailMode.VIEW -> 12.dp
        NoteDetailMode.EDIT -> 70.dp
        NoteDetailMode.READ -> 12.dp
    }

    val scrollState = rememberScrollState()

    LaunchedEffect(state.noteMode) {
        if(state.noteMode == NoteDetailMode.READ) {
            snapshotFlow { scrollState.isScrollInProgress }
                .distinctUntilChanged()
                .filter { it }
                .collect {
                    onAction(NoteDetailAction.OnScrollStarted)
                }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 20.dp,
                start = 16.dp,
                end = 16.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(
            spaceArrangement,
            Alignment.CenterHorizontally
        ),
    ) {
        when (state.noteMode) {
            NoteDetailMode.VIEW -> {
                NavigationButton(
                    icon = Icons.AutoMirrored.Filled.ArrowBackIos,
                    onClick = {
                        onAction(NoteDetailAction.OnCloseClicked)
                    }
                )

                NoteMarkDefaultTextButton(
                    text = stringResource(R.string.all_notes),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    uppercase = true,
                    textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    onClick = {
                        onAction(NoteDetailAction.OnCloseClicked)
                    },
                )

                NoteViewMode(
                    modifier = Modifier
                        .weight(1f),
                    state = state,
                    isTablet = isTablet,
                )

                NoteMarkDefaultTextButton(
                    modifier = Modifier
                        .alpha(0f),
                    text = stringResource(R.string.save_note),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    uppercase = true,
                    onClick = {
                        onAction(NoteDetailAction.OnSaveNoteClicked)
                    },
                )
            }

            NoteDetailMode.EDIT -> {
                NavigationButton(
                    icon = Icons.Default.Close,
                    onClick = {
                        onAction(NoteDetailAction.OnCloseClicked)
                    }
                )

                NoteEditMode(
                    modifier = Modifier
                        .weight(1f)
                        .imePadding(),
                    state = state,
                    onAction = onAction,
                )
            }

            NoteDetailMode.READ -> {
                FadeVisibilityComponent(
                    isVisible = state.areUiElementsVisible
                ) {
                    NavigationButton(
                        icon = Icons.AutoMirrored.Filled.ArrowBackIos,
                        onClick = {
                            onAction(if(state.areUiElementsVisible) NoteDetailAction.OnCloseClicked else NoteDetailAction.OnReadModeTap)
                        },
                    )
                }

                FadeVisibilityComponent(
                    isVisible = state.areUiElementsVisible,
                ) {
                    NoteMarkDefaultTextButton(
                        text = stringResource(R.string.all_notes),
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                        uppercase = true,
                        textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        onClick = {
                            onAction(if(state.areUiElementsVisible) NoteDetailAction.OnCloseClicked else NoteDetailAction.OnReadModeTap)
                        },
                    )
                }

                NoteViewMode(
                    modifier = Modifier
                        .weight(1f),
                    state = state,
                    isTablet = isTablet,
                    scrollState = scrollState
                )

                FadeVisibilityComponent(
                    isVisible = false
                ) {
                    NoteMarkDefaultTextButton(
                        text = stringResource(R.string.save_note),
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                        uppercase = true,
                        onClick = {
                            onAction(NoteDetailAction.OnReadModeTap)
                        },
                    )
                }
            }
        }
    }
}

@MobileLandscape
@TabletLandscape
@Composable
fun NoteDetailLandscapeViewPreview() {
    NoteMarkTheme {
        NoteDetailLandscapeView(
            state = NoteDetailState(
                title = "Note Title",
                createdAt = UiText.DynamicString("2021-01-01"),
                lastEditedAt = UiText.DynamicString("2021-01-01"),
                content = "Note Content"
            ),
        )
    }
}