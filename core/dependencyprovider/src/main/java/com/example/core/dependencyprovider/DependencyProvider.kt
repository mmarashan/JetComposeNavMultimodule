package com.example.core.dependencyprovider

import com.example.feature.home.api.HomeFeatureApi
import com.example.feature.home.impl.HomeFeatureImpl
import com.example.feature.onboarding.api.OnboardingFeatureApi
import com.example.feature.onboarding.impl.OnboardingFeatureImpl
import com.example.feature.settings.api.SettingsFeatureApi
import com.example.feature.settings.impl.SettingsFeatureImpl

/**
 * WARNING!!! Don't use it in real project! Use real DI libraries: Dagger, Hilt, Koin..
 * We did this to simplify the example
 */
object DependencyProvider {

    /* Don't use lateinit in real project :) */
    private lateinit var homeImpl: HomeFeatureApi
    private lateinit var settingsImpl: SettingsFeatureApi
    private lateinit var onboardingImpl: OnboardingFeatureApi

    fun provideDependencies(
        homeImpl: HomeFeatureApi,
        settingsImpl: SettingsFeatureApi,
        onboardingImpl: OnboardingFeatureApi
    ) {
        this.homeImpl = homeImpl
        this.settingsImpl = settingsImpl
        this.onboardingImpl = onboardingImpl
    }

    fun homeFeature(): HomeFeatureApi = homeImpl

    fun settingsFeature(): SettingsFeatureApi = settingsImpl

    fun onboardingFeature(): OnboardingFeatureApi = onboardingImpl
}