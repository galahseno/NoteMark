package com.icdid.dashboard.presentation.note_detail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.ime
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.dashboard.presentation.R
import com.icdid.dashboard.presentation.note_detail.NoteDetailAction
import com.icdid.dashboard.presentation.note_detail.NoteDetailState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@Composable
fun NoteEditMode(
    modifier: Modifier = Modifier,
    isTablet: Boolean = false,
    state: NoteDetailState = NoteDetailState(),
    onAction: (NoteDetailAction) -> Unit = {},
) {
    val scrollState = rememberScrollState()
    val horizontalPadding = if (isTablet) 24.dp else 16.dp

    var textState by remember { mutableStateOf(TextFieldValue(state.title)) }
    var contentState by remember {
        mutableStateOf(TextFieldValue(state.content))
    }

    val focusTitleRequester = remember { FocusRequester() }
    val focusContentRequester = remember { FocusRequester() }
    var isContentFocused by remember { mutableStateOf(false) }

    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val imeBottom = WindowInsets.ime.asPaddingValues().calculateBottomPadding()

    val coroutineScope = rememberCoroutineScope()
    var lastContentHeightPx by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        focusTitleRequester.requestFocus()
        textState = textState.copy(
            selection = TextRange(textState.text.length)
        )
    }

    LaunchedEffect(isContentFocused) {
        if (isContentFocused) {
            contentState = contentState.copy(
                selection = TextRange(contentState.text.length)
            )

            if(!imeVisible) {
                snapshotFlow { imeBottom }.
                distinctUntilChanged()
                    .debounce(300)
                    .collectLatest {
                        scrollState.animateScrollTo(scrollState.maxValue)
                    }
            } else {
                scrollState.animateScrollTo(scrollState.maxValue)
            }
        }
    }

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .imePadding()
    ) {
        BasicTextField(
            modifier = Modifier
                .padding(
                    vertical = 20.dp, horizontal = horizontalPadding
                )
                .focusRequester(focusTitleRequester),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            value = textState,
            onValueChange = {
                onAction(NoteDetailAction.OnNoteTitleChanged(it.text))
                textState = it
            },
            decorationBox = { innerTextField ->
                if (state.title.isEmpty()) {
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
                    vertical = 20.dp, horizontal = horizontalPadding
                )
                .focusRequester(focusContentRequester)
                .onFocusChanged { focusState ->
                    isContentFocused = focusState.isFocused
                },
            onTextLayout = { textLayoutResult ->
                val height = textLayoutResult.size.height
                if (height != lastContentHeightPx) {
                    lastContentHeightPx = height
                    if(isContentFocused) {
                        coroutineScope.launch {
                            scrollState.animateScrollTo(scrollState.maxValue)
                        }
                    }
                }
            },
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            value = contentState,
            onValueChange = {
                contentState = it
                onAction(NoteDetailAction.OnNoteContentChanged(it.text))
            },
            decorationBox = { innerTextField ->
                if (state.content.isEmpty()) {
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
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteEditModePrev() {
    NoteEditMode()
}