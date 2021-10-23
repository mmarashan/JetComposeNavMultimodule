package com.example.core.dependencyprovider

import com.example.api.onboarding.OnboardingFeatureApi
import com.example.feature.api.home.HomeFeatureApi
import com.example.feature.api.settings.SettingsFeatureApi

/**
 * WARNING!!! Don't use it in real project! Use real DI libraries: Dagger, Hilt, Koin..
 * We did this to simplify the example
 */
object DependencyProvider {

    /* Don't use lateinit in real project :) */
    private lateinit var homeFeatureApi: HomeFeatureApi
    private lateinit var settingsFeatureApi: SettingsFeatureApi
    private lateinit var onboardingFeatureApi: OnboardingFeatureApi

    fun provideImpl(
        homeFeatureApi: HomeFeatureApi,
        settingsFeatureApi: SettingsFeatureApi,
        onboardingFeatureApi: OnboardingFeatureApi
    ) {
        this.homeFeatureApi = homeFeatureApi
        this.settingsFeatureApi = settingsFeatureApi
        this.onboardingFeatureApi = onboardingFeatureApi
    }

    fun homeFeature(): HomeFeatureApi = homeFeatureApi

    fun settingsFeature(): SettingsFeatureApi = settingsFeatureApi

    fun onboardingFeature(): OnboardingFeatureApi = onboardingFeatureApi
}