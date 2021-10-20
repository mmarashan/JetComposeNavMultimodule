package com.example.jetcomposenavmultimodule.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetcomposenavmultimodule.ui.theme.JetComposeNavMultimoduleTheme
import com.example.jetcomposenavmultimodule.ui.theme.Purple200
import com.example.jetcomposenavmultimodule.ui.theme.backgroundWhite
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.navigationBarsPadding
import java.util.Locale

@Composable
fun AppContent() {
    ProvideWindowInsets {
        JetComposeNavMultimoduleTheme {
            val tabs = remember { BottomTabs.values() }
            val navController = rememberNavController()
            Scaffold(
                backgroundColor = backgroundWhite,
                bottomBar = { BottomBar(navController = navController, tabs) }
            ) { innerPaddingModifier ->
                AppNavGraph(
                    navController = navController,
                    modifier = Modifier.padding(innerPaddingModifier)
                )
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController, tabs: Array<BottomTabs>) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: BottomTabs.HOME.route

    val routes = remember { BottomTabs.values().map { it.route } }
    if (currentRoute in routes) {
        BottomNavigation(
            Modifier.navigationBarsHeight(additional = 56.dp)
        ) {
            tabs.forEach { tab ->
                BottomNavigationItem(
                    icon = { Icon(painterResource(tab.icon), contentDescription = null) },
                    label = { Text(stringResource(tab.title).uppercase(Locale.getDefault())) },
                    selected = currentRoute == tab.route,
                    onClick = {
                        if (tab.route != currentRoute) {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    alwaysShowLabel = false,
                    selectedContentColor = MaterialTheme.colors.secondary,
                    unselectedContentColor = LocalContentColor.current,
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }
    }
}
