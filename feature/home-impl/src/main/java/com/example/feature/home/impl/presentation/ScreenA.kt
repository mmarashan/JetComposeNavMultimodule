package com.example.feature.home.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun ScreenA(
    modifier: Modifier,
    onNavigateNextWithArgument: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            "Screen A. Input parameter value",
            modifier = Modifier.padding(36.dp),
            fontSize = 24.sp
        )

        OutlinedTextField(
            value = text,
            onValueChange = { value -> text = value },
            modifier = Modifier.padding(36.dp)
        )

        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                onNavigateNextWithArgument(text)
            }
        ) {
            Text("To screen B")
        }
    }
}