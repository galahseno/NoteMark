package com.icdid.core.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.hideFromAccessibility
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.core.presentation.R
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.theme.error

@Composable
fun NoteMarkTextField(
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    isPassword: Boolean = false,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    errorMessage: String? = null,
    supportingText: String? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    var showPassword by remember { mutableStateOf(isPassword) }
    var textValue by remember { mutableStateOf(value) }
    val borderColor =
        if (errorMessage != null && !isFocused) error else if (isFocused) MaterialTheme.colorScheme.primary else Color.Transparent

    Column(
        modifier = modifier
    ) {
        // region label
        label?.let {
            Text(
                modifier = Modifier
                    .semantics {
                        hideFromAccessibility()
                    },
                text = label,
                style = LocalNoteMarkTypography.current.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                )
            )

            Spacer(modifier = Modifier.size(7.dp))
        }
        // end region

        // region TextField
        BasicTextField(
            value = textValue,
            modifier = Modifier
                .semantics {
                    label?.let {
                        this.contentDescription = it
                    }
                },
            onValueChange = {
                textValue = it
                onValueChange(it)
            },
            cursorBrush = SolidColor(if (errorMessage != null && !isFocused) error else MaterialTheme.colorScheme.primary),
            singleLine = true,
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = borderColor,
                            shape = RoundedCornerShape(12.dp),
                        )
                        .background(
                            color = if (isFocused) Color.Transparent else MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(12.dp),
                        )
                        .padding(
                            top = 12.dp,
                            bottom = 12.dp,
                            start = 16.dp,
                            end = 14.dp,
                        )
                ) {
                    innerTextField()

                    if ((isFocused && textValue.isEmpty()) || (!isFocused && textValue.isEmpty())) {
                        placeholder?.let {
                            Text(
                                modifier = Modifier.
                                    semantics {
                                        hideFromAccessibility()
                                    },
                                text = placeholder,
                                style = LocalNoteMarkTypography.current.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            )
                        }
                    }

                    if (isPassword) {
                        IconButton(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.TopEnd),
                            onClick = { showPassword = !showPassword }
                        ) {
                            Icon(
                                imageVector = if (showPassword) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = if(showPassword) stringResource(R.string.show_password) else stringResource(R.string.hide_password),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            },
            textStyle = LocalNoteMarkTypography.current.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
            ),
            visualTransformation = if (showPassword) PasswordVisualTransformation() else VisualTransformation.None,
        )
        // end region

        // region Error message
        errorMessage?.let { errorMessage ->
            AnimatedVisibility(
                visible = !isFocused && errorMessage.isNotBlank(),
            ) {
                Spacer(modifier = Modifier.size(7.dp))

                Text(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .semantics {
                            this.contentDescription = errorMessage
                            this.liveRegion = LiveRegionMode.Polite
                        },
                    text = errorMessage,
                    style = LocalNoteMarkTypography.current.bodySmall.copy(
                        color = error
                    )
                )
            }
        }
        // end region

        // region Supporting text
        supportingText?.let { supportingText ->
            AnimatedVisibility(
                visible = isFocused,
            ) {
                Spacer(modifier = Modifier.size(7.dp))
                Text(
                    modifier = Modifier
                        .semantics {
                            this.contentDescription = supportingText
                            this.liveRegion = LiveRegionMode.Assertive
                        }
                        .padding(start = 12.dp),
                    text = supportingText,
                    style = LocalNoteMarkTypography.current.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
        // end region
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteMarkTextFieldPreview() {
    NoteMarkTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NoteMarkTextField(
                label = "email",
                placeholder = "john.doe@example.com",
            )
            NoteMarkTextField(
                label = "password",
                isPassword = true,
            )
            NoteMarkTextField(
                label = "Text field with error",
                errorMessage = "Password must be at least 8 characters and include a number or symbol"
            )
            NoteMarkTextField(
                label = "Text field with supporting text",
                supportingText = "Password must be at least 8 characters and include a number or symbol"
            )
        }
    }
}