package com.example.feature.onboarding.impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.feature.onboarding.api.OnboardingFeatureApi

class OnboardingFeatureImpl : OnboardingFeatureApi {

    private val route = "onboarding"

    override fun route() = route

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route) {
            OnboardingScreen(modifier = modifier)
        }
    }
}