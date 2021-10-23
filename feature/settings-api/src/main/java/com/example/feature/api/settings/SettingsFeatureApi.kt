package com.example.feature.api.settings

import com.example.core.feature_api.FeatureApi

interface SettingsFeatureApi: FeatureApi {

    fun settingsRoute(): String
}