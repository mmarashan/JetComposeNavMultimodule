package com.example.feature.home.impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.feature.api.home.HomeFeatureApi

class HomeFeatureImpl : HomeFeatureApi {

    private val parameterKey = "parameterKey"
    private val baseDetailsRoute = "homeDetails"

    /**/
    private val baseHomeMainRoute = "homeMain"
    private val baseRoute = "home"

    override fun homeRoute() = baseHomeMainRoute

    override fun homeDetails(parameter: String) = "$baseDetailsRoute/${parameter}"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.navigation(route = baseRoute, startDestination = baseHomeMainRoute) {

            composable(baseHomeMainRoute) {
                HomeScreen(modifier = modifier, navController = navController)
            }

            composable(
                route = "$baseDetailsRoute/{$parameterKey}",
                arguments = listOf(navArgument(parameterKey) { type = NavType.StringType })
            ) { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                val parameter = arguments.getString(parameterKey)
                HomeDetailsScreen(modifier = modifier, parameter = parameter.orEmpty())
            }
        }
    }
}