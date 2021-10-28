package com.example.feature.home.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeDetailsScreen(modifier: Modifier, argument: String) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            "Home details",
            modifier = Modifier.padding(36.dp),
            fontSize = 24.sp
        )
        Text(
            "Argument = $argument",
            modifier = Modifier.padding(16.dp),
            fontSize = 24.sp
        )
    }
}