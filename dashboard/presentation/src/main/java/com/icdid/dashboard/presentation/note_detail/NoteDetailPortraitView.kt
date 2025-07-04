package com.icdid.dashboard.presentation.note_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icdid.core.presentation.composables.NoteMarkDefaultTextButton
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.MobilePortrait
import com.icdid.core.presentation.utils.TabletPortrait
import com.icdid.core.presentation.utils.UiText
import com.icdid.dashboard.presentation.R
import com.icdid.dashboard.presentation.note_detail.composables.NavigationButton
import com.icdid.dashboard.presentation.note_detail.composables.NoteFormView
import com.icdid.dashboard.presentation.note_detail.composables.NoteViewMode
import com.icdid.dashboard.presentation.note_detail.model.NoteDetailMode

@Composable
fun NoteDetailPortraitView(
    isTablet: Boolean = false,
    state: NoteDetailState = NoteDetailState(),
    onAction: (NoteDetailAction) -> Unit = {},
) {
    val horizontalPadding = if(isTablet) 20.dp else 16.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(
                    vertical = 18.dp,
                    horizontal = horizontalPadding,
                )
        ) {
            when(state.noteMode) {
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

                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                    )
                }
                NoteDetailMode.EDIT -> {
                    NavigationButton(
                        onClick = { onAction(NoteDetailAction.OnCloseClicked) }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    NoteMarkDefaultTextButton(
                        text = stringResource(R.string.save_note),
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                        uppercase = true,
                        onClick = {
                            onAction(NoteDetailAction.OnSaveNoteClicked)
                        }
                    )
                }
                NoteDetailMode.READ -> {
                    // TODO Read mode UI
                }
            }
        }

        when(state.noteMode) {
            NoteDetailMode.VIEW -> {
                NoteViewMode(
                    state = state,
                    isTablet = isTablet,
                )
            }
            NoteDetailMode.EDIT -> {
                NoteFormView(
                    isTablet = isTablet,
                    state = state,
                    onAction = onAction,
                )
            }
            NoteDetailMode.READ -> {
                // TODO Read mode UI
            }
        }
    }
}

@MobilePortrait
@TabletPortrait
@Composable
private fun NoteDetailPortraitViewMobilePreview() {
    NoteMarkTheme {
        NoteDetailPortraitView(
            state = NoteDetailState(
                title = "Note Title",
                createdAt = UiText.DynamicString("2021-01-01"),
                lastEditedAt = UiText.DynamicString("2021-01-01"),
                content = "Note Content"
            ),
        )
    }
}