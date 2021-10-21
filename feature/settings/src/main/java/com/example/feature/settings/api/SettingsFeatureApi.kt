package com.example.feature.settings.api

import com.example.core.feature_api.FeatureApi

interface SettingsFeatureApi: FeatureApi {

    fun settingsRoute(): String
}