package com.example.feature.settings.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun SettingsScreen(modifier: Modifier) {
    Box(modifier = modifier) {
        Text("Settings", modifier = Modifier.padding(36.dp))
    }
}