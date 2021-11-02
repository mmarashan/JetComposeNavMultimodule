package com.example.feature.home.impl.internal

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.core.feature_api.FeatureApi
import com.example.feature.home.impl.ScreenA
import com.example.feature.home.impl.ScreenB

/**
 * Внутренне API фичи для навигации по внутренним экранам
 */
internal object InternalHomeFeatureApi : FeatureApi {

    private const val scenarioABRoute = "home/scenarioABRoute"
    private const val parameterKey = "parameterKey"
    private const val screenBRoute = "home/screenB"
    private const val screenARoute = "home/screenA"

    fun scenarioABRoute() = scenarioABRoute

    fun screenA() = screenARoute

    fun screenB(parameter: String) = "$screenBRoute/${parameter}"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {

        navGraphBuilder.navigation(
            route = scenarioABRoute,
            startDestination = screenARoute
        ) {

            composable(route = screenARoute) {
                ScreenA(modifier = modifier, navController = navController)
            }

            composable(
                route = "$screenBRoute/{$parameterKey}",
                arguments = listOf(navArgument(parameterKey) { type = NavType.StringType })
            ) { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                val argument = arguments.getString(parameterKey)
                ScreenB(modifier = modifier, argument = argument.orEmpty())
            }
        }
    }
}