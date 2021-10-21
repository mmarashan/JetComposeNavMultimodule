package com.example.feature.home.impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.feature.home.api.HomeFeatureApi

class HomeFeatureImpl : HomeFeatureApi {

    private val route = "home"

    override fun homeRoute() = route

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route) {
            HomeScreen(modifier = modifier)
        }
    }
}