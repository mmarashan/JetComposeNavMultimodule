package com.example.feature.onboarding.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun OnboardingScreen(
    modifier: Modifier,
    onNavigateToHome: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello world! You're in onboarding screen",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(36.dp),
            fontSize = 24.sp
        )
        SimpleButton(
            text = "To home",
            onClick = onNavigateToHome
        )

        SimpleButton(
            text = "To settings",
            onClick = onNavigateToSettings
        )
    }
}

@Composable
private fun SimpleButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.padding(16.dp),
        onClick = onClick
    ) {
        Text(text)
    }
}