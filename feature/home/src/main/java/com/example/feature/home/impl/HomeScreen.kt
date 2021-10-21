package com.example.feature.home.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(modifier: Modifier) {
    Box(modifier = modifier) {
        Text("Home", modifier = Modifier.padding(36.dp))
    }
}