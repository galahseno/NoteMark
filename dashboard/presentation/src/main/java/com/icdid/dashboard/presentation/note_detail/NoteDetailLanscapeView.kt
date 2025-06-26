package com.icdid.dashboard.presentation.note_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icdid.core.presentation.composables.NoteMarkDefaultTextButton
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.MobileLandscape
import com.icdid.core.presentation.utils.TabletLandscape
import com.icdid.dashboard.presentation.R
import com.icdid.dashboard.presentation.note_detail.composables.CloseButton
import com.icdid.dashboard.presentation.note_detail.composables.NoteFormView

@Composable
fun NoteDetailLandscapeView(
    state: NoteDetailState = NoteDetailState(),
    onAction: (NoteDetailAction) -> Unit = {},
    focusRequester: FocusRequester = FocusRequester(),
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 20.dp,
                start = 16.dp,
                end = 16.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(70.dp, Alignment.CenterHorizontally),
    ) {
        CloseButton(
            onClick = {
                onAction(NoteDetailAction.OnCloseClicked)
            }
        )

        NoteFormView(
            modifier = Modifier
                .weight(1f)
                .imePadding(),
            state = state,
            onAction = onAction,
            focusRequester = focusRequester
        )

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
            },
        )
    }
}

@MobileLandscape
@TabletLandscape
@Composable
fun NoteDetailLandscapeViewPreview() {
    NoteMarkTheme {
        NoteDetailLandscapeView()
    }
}