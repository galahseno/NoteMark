package com.icdid.notemark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.core.presentation.composables.NoteMarkOutlinedButton
import com.icdid.core.presentation.composables.NoteMarkPrimaryButton
import com.icdid.core.presentation.composables.NoteMarkTextField
import com.icdid.core.presentation.theme.NoteMarkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteMarkTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    TestScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TestScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {

        Column(
            verticalArrangement = Arrangement
                .spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            NoteMarkTextField(
                label = "Email",
                placeholder = "john.doe@example.com",
            )

            NoteMarkTextField(
                label = "Password",
                isPassword = true,
                supportingText = stringResource(R.string.password_details)
            )

            NoteMarkTextField(
                label = "Textfield error",
                errorMessage = "Password must be at least 8 characters and include a number or symbol",
            )

            NoteMarkPrimaryButton(
                text = "Getting started",
            )

            NoteMarkOutlinedButton(
                text = "Login"
            )
        }
    }
}

@Preview
@Composable
fun TestScreenPreview() {
    TestScreen()
}