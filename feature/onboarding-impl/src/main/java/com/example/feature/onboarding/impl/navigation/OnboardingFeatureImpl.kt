package com.example.feature.onboarding.impl.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.api.onboarding.OnboardingFeatureApi
import com.example.core.dependencyprovider.DependencyProvider
import com.example.feature.onboarding.impl.OnboardingScreen

class OnboardingFeatureImpl : OnboardingFeatureApi {

    override val route = "onboarding"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route) {
            OnboardingScreen(
                modifier = modifier,
                onNavigateToHome = {
                    val homeFeature = DependencyProvider.homeFeature()
                    navController.navigate(homeFeature.homeRoute) {
                        popUpTo(route) { inclusive = true }
                    }
                },
                onNavigateToSettings = {
                    val settingsFeature = DependencyProvider.settingsFeature()
                    navController.navigate(settingsFeature.settingsRoute) {
                        popUpTo(route) { inclusive = true }
                    }
                }
            )
        }
    }
}