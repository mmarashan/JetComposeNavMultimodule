package com.example.feature.onboarding.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingScreen(modifier: Modifier) {
    Box(modifier = modifier) {
        Text("Hello world!", modifier = Modifier.padding(36.dp))
        Button(onClick = { /*TODO*/ }) {
            Text("Next", modifier = Modifier.padding(8.dp))
        }
    }
}