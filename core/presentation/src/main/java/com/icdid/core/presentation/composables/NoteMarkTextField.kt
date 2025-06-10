package com.icdid.core.presentation.composables

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
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

    val showPassword = remember { mutableStateOf(isPassword) }
    val textValue = remember { mutableStateOf(value) }
    val borderColor =
        if (errorMessage != null) error else if (isFocused) MaterialTheme.colorScheme.primary else Color.Transparent

    Column(
        modifier = modifier
    ) {
        // region label
        label?.let {
            Text(
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
            value = textValue.value,
            onValueChange = {
                textValue.value = it
                onValueChange(it)
            },
            cursorBrush = SolidColor(if(errorMessage != null) error else MaterialTheme.colorScheme.primary),
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

                    if (!isFocused || textValue.value.isEmpty()) {
                        placeholder?.let {
                            Text(
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
                            onClick = { showPassword.value = !showPassword.value }
                        ) {
                            Icon(
                                imageVector = if (showPassword.value) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            },
            textStyle = LocalNoteMarkTypography.current.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
            ),
            visualTransformation = if (showPassword.value) PasswordVisualTransformation() else VisualTransformation.None,
        )
        // end region

        // region Error message
        errorMessage?.let {
            Spacer(modifier = Modifier.size(7.dp))

            Text(
                modifier = Modifier
                    .padding(start = 12.dp),
                text = it,
                style = LocalNoteMarkTypography.current.bodySmall.copy(
                    color = error
                )
            )
        }
        // end region

        // region Supporting text
        supportingText?.let {
            Spacer(modifier = Modifier.size(7.dp))
            Text(
                modifier = Modifier
                    .padding(start = 12.dp),
                text = it,
                style = LocalNoteMarkTypography.current.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
        // end region
    }
}

@Preview
@Composable
fun NoteMarkTextFieldPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
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