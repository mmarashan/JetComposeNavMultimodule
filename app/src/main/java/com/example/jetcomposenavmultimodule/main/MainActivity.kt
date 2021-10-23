package com.example.jetcomposenavmultimodule.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.core.dependencyprovider.DependencyProvider
import com.example.feature.home.impl.HomeFeatureImpl
import com.example.feature.onboarding.impl.OnboardingFeatureImpl
import com.example.feature.settings.impl.SettingsFeatureImpl
import com.example.jetcomposenavmultimodule.ui.theme.JetComposeNavMultimoduleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)

        DependencyProvider.provideImpl(
            homeFeatureApi = HomeFeatureImpl(),
            settingsFeatureApi = SettingsFeatureImpl(),
            onboardingFeatureApi = OnboardingFeatureImpl()
        )

        setContent {
            JetComposeNavMultimoduleTheme {
                AppContent()
            }
        }
    }
}