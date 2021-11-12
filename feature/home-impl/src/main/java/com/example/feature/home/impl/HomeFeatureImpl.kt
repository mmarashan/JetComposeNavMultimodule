package com.example.feature.home.impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.feature.api.home.HomeFeatureApi
import com.example.feature.home.impl.internal.InternalHomeFeatureApi

/**
 * Реализация API фичи home
 */
class HomeFeatureImpl : HomeFeatureApi {

    private val baseRoute = "home"

    override fun homeRoute() = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {

        navGraphBuilder.composable(baseRoute) {
            HomeScreen(modifier = modifier, navController = navController)
        }

        InternalHomeFeatureApi.registerGraph(
            navGraphBuilder = navGraphBuilder,
            navController = navController,
            modifier = modifier
        )
    }
}