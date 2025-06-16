package com.icdid.dashboard.presentation.note_detail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.icdid.core.presentation.composables.NoteMarkDefaultTextButton
import com.icdid.core.presentation.utils.MobilePortrait
import com.icdid.core.presentation.utils.TabletPortrait
import com.icdid.dashboard.presentation.R
import com.icdid.dashboard.presentation.note_detail.composables.CloseButton
import com.icdid.dashboard.presentation.note_detail.composables.NoteFormView

@Composable
fun NoteDetailPortraitView(
    isTablet: Boolean = false,
    state: NoteDetailState = NoteDetailState(),
    onAction: (NoteDetailAction) -> Unit = {},
    focusRequester: FocusRequester = FocusRequester(),
) {
    val horizontalPadding = if(isTablet) 20.dp else 16.dp

    Row(
        modifier = Modifier
            .padding(
                vertical = 18.dp,
                horizontal = horizontalPadding,
            )
    ) {
        CloseButton(
            onClick = { onAction(NoteDetailAction.OnCloseClicked) }
        )

        Spacer(modifier = Modifier.weight(1f))

        NoteMarkDefaultTextButton(
            text = stringResource(R.string.save_note),
            onClick = { }
        )
    }

    NoteFormView(
        isTablet = isTablet,
        state = state,
        onAction = onAction,
        focusRequester = focusRequester
    )
}


@MobilePortrait
@Composable
private fun NoteDetailPortraitViewMobilePreview() {
    NoteDetailPortraitView()
}

@TabletPortrait
@Composable
private fun NoteDetailPortraitViewTabletPreview() {
    NoteDetailPortraitView()
}