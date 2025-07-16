package com.icdid.dashboard.presentation.note_detail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.dashboard.presentation.R
import com.icdid.dashboard.presentation.note_detail.NoteDetailAction
import com.icdid.dashboard.presentation.note_detail.model.NoteDetailMode

@Composable
fun NoteDetailModeFAB(
    modifier: Modifier = Modifier,
    noteDetailMode: NoteDetailMode,
    onAction: (NoteDetailAction) -> Unit,
    isVisible: Boolean = true,
) {
    Surface(
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .sizeIn(minWidth = 100.dp, minHeight = 52.dp)
    ) {
        Row(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (noteDetailMode == NoteDetailMode.EDIT) MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.1f
                        )
                        else Color.Transparent
                    )
                    .clickable {
                        when (noteDetailMode) {
                            NoteDetailMode.VIEW,
                            NoteDetailMode.READ ->
                                onAction(
                                    if (isVisible) {
                                        NoteDetailAction.OnChangeMode(NoteDetailMode.EDIT)
                                    } else {
                                        NoteDetailAction.OnReadModeTap
                                    }
                                )

                            NoteDetailMode.EDIT -> onAction(
                                NoteDetailAction.OnChangeMode(
                                    NoteDetailMode.VIEW
                                )
                            )
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.edit_mode),
                    contentDescription = null,
                    tint = if (noteDetailMode == NoteDetailMode.EDIT) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (noteDetailMode == NoteDetailMode.READ) MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.1f
                        )
                        else Color.Transparent
                    )
                    .weight(1f)
                    .clickable {
                        when (noteDetailMode) {
                            NoteDetailMode.VIEW,
                            NoteDetailMode.EDIT -> onAction(
                                if(isVisible) {
                                    NoteDetailAction.OnChangeMode(
                                        NoteDetailMode.READ
                                    )
                                } else {
                                    NoteDetailAction.OnReadModeTap
                                }
                            )
                            NoteDetailMode.READ -> onAction(
                                NoteDetailAction.OnChangeMode(
                                    NoteDetailMode.VIEW
                                )
                            )
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.read_mode),
                    contentDescription = null,
                    tint = if (noteDetailMode == NoteDetailMode.READ) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteDetailModeFABPreview() {
    NoteMarkTheme {
        NoteDetailModeFAB(
            noteDetailMode = NoteDetailMode.VIEW,
            onAction = {}
        )
    }
}