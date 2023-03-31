package com.example.feature.settings.impl.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.feature.api.settings.SettingsFeatureApi
import com.example.feature.settings.impl.SettingsScreen

class SettingsFeatureImpl : SettingsFeatureApi {

    override val settingsRoute: String = "settings"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(settingsRoute) {
            SettingsScreen(modifier = modifier)
        }
    }
}