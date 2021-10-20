package com.example.jetcomposenavmultimodule.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jetcomposenavmultimodule.ui.theme.backgroundWhite


@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            Box(modifier = modifier) {
                Text("home")
            }
        }

        composable("settings") {
            Box(modifier = modifier) {
                Text("settings")
            }
        }

    }
}
