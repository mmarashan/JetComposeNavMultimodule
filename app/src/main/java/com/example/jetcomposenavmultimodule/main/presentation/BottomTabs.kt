package com.example.jetcomposenavmultimodule.main.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.core.dependencyprovider.DependencyProvider
import com.example.jetcomposenavmultimodule.R

enum class BottomTabs(
    @StringRes
    val title: Int,
    @DrawableRes
    val icon: Int,
    val route: String
) {

    HOME(
        R.string.home,
        R.drawable.ic_baseline_home,
        DependencyProvider.homeFeature().homeRoute
    ),
    SETTINGS(
        R.string.settings,
        R.drawable.ic_baseline_settings,
        DependencyProvider.settingsFeature().settingsRoute
    )
}
