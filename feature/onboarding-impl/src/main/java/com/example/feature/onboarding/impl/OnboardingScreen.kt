package com.example.feature.onboarding.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.core.dependencyprovider.DependencyProvider

@Composable
internal fun OnboardingScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello world! You're in onboarding screen",
            modifier = Modifier.padding(36.dp)
        )
        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                val homeFeature = DependencyProvider.homeFeature()
                navController.popBackStack()
                navController.navigate(homeFeature.homeRoute())
            }) {
            Text("To home")
        }

        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                val settingsFeature = DependencyProvider.settingsFeature()
                navController.popBackStack()
                navController.navigate(settingsFeature.settingsRoute())
            }) {
            Text("To settings")
        }
    }
}