package com.example.core.dependencyprovider

import com.example.feature.home.api.HomeFeatureApi
import com.example.feature.home.impl.HomeFeatureImpl
import com.example.feature.settings.api.SettingsFeatureApi
import com.example.feature.settings.impl.SettingsFeatureImpl

/**
 * WARNING!!! Don't use it in real project! Use real DI libraries: Dagger, Hilt, Koin..
 * We did this to simplify the example
 */
object DependencyProvider {

    fun homeFeature(): HomeFeatureApi = HomeFeatureImpl()

    fun settingsFeature(): SettingsFeatureApi = SettingsFeatureImpl()

    fun onboardingFeature(): SettingsFeatureApi = SettingsFeatureImpl()
}