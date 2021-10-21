package com.example.feature.onboarding.impl

import android.util.Log
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.feature.onboarding.api.OnboardingFeatureApi
import java.lang.Exception

class OnboardingFeatureImpl : OnboardingFeatureApi {

    private val route = "onboarding"

    override fun route() = route

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route) {
            OnboardingScreen {
                navController.navigate("home")
            }
        }
    }
}