package com.example.feature.home.impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.feature.api.home.HomeFeatureApi

class HomeFeatureImpl : HomeFeatureApi {

    private val parameterKey = "parameterKey"
    private val baseRoute = "home"

    override fun homeRoute() = baseRoute

    override fun homeDetails(parameter: String): String = "$baseRoute/$parameter"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(baseRoute) { backStackEntry ->
            HomeScreen(modifier = modifier, navController = navController)
        }

        navGraphBuilder.composable("$baseRoute/{$parameterKey}") { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val parameter = arguments.getString(parameterKey)
            HomeDetailsScreen(modifier = modifier, parameter = parameter.orEmpty())
        }
    }
}