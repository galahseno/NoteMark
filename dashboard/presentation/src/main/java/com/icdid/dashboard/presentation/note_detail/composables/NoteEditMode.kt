package com.icdid.dashboard.presentation.note_detail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.dashboard.presentation.R
import com.icdid.dashboard.presentation.note_detail.NoteDetailAction
import com.icdid.dashboard.presentation.note_detail.NoteDetailState

@Composable
fun NoteFormView(
    modifier: Modifier = Modifier,
    isTablet: Boolean = false,
    state: NoteDetailState = NoteDetailState(),
    onAction: (NoteDetailAction) -> Unit = {},
    isEditable: Boolean = true,
) {
    val scrollState = rememberScrollState()
    val horizontalPadding = if(isTablet) 24.dp else 16.dp

    var textState by remember { mutableStateOf(TextFieldValue("")) }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if(isEditable) {
            focusRequester.requestFocus()
        }
    }
    
    LaunchedEffect(key1 = state.title) {
        if(isEditable) {
            if(state.title.isNotEmpty()) {
                textState = TextFieldValue(
                    text = state.title,
                    selection = TextRange(state.title.length)
                )
            }
        }
    }

    LaunchedEffect(key1 = state.content) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .imePadding()
    ) {
        BasicTextField(
            modifier = Modifier
                .padding(
                    vertical = 20.dp,
                    horizontal = horizontalPadding
                )
                .focusRequester(focusRequester),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            value = textState,
            onValueChange = {
                onAction(NoteDetailAction.OnNoteTitleChanged(it.text))
                textState = it.copy(selection = TextRange(it.text.length))
            },
            decorationBox = { innerTextField ->
                if(state.title.isEmpty()) {
                    Text(
                        text = stringResource(R.string.note_title),
                        style = LocalNoteMarkTypography.current.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
                innerTextField()
            },
            singleLine = true,
            textStyle = LocalNoteMarkTypography.current.titleLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            enabled = isEditable,
        )

        HorizontalDivider(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface
                ),
            thickness = 1.dp,
        )

        BasicTextField(
            modifier = Modifier
                .padding(
                    vertical = 20.dp,
                    horizontal = horizontalPadding
                ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            value = state.content,
            onValueChange = {
                onAction(NoteDetailAction.OnNoteContentChanged(it))
            },
            decorationBox = { innerTextField ->
                if(state.content.isEmpty()) {
                    Text(
                        text = stringResource(R.string.tap_to_enter_note_content),
                        style = LocalNoteMarkTypography.current.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
                innerTextField()
            },
            textStyle = LocalNoteMarkTypography.current.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            enabled = isEditable,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteFormViewPrev() {
    NoteFormView()
}