package com.example.core.dependencyprovider

import com.example.feature.api.home.HomeFeatureApi
import com.example.feature.home.impl.HomeFeatureImpl
import com.example.feature.api.settings.SettingsFeatureApi
import com.example.feature.settings.impl.SettingsFeatureImpl

/**
 * WARNING!!! Don't use it in real project! Use real DI libraries: Dagger, Hilt, Koin..
 * We did this to simplify the example
 */
object DependencyProvider {

    fun homeFeature():  HomeFeatureApi = HomeFeatureImpl()

    fun settingsFeature(): SettingsFeatureApi = SettingsFeatureImpl()
}