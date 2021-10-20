package com.example.jetcomposenavmultimodule.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.jetcomposenavmultimodule.R

enum class BottomTabs(
    @StringRes
    val title: Int,
    @DrawableRes
    val icon: Int,
    val route: String
) {

    HOME(R.string.home, R.drawable.ic_baseline_home, "home"),
    SETTINGS(R.string.settings, R.drawable.ic_baseline_settings, "settings")
}
