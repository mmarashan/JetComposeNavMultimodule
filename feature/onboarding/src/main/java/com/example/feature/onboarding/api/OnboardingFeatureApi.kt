package com.example.feature.onboarding.api

import com.example.core.feature_api.FeatureApi

interface OnboardingFeatureApi: FeatureApi {

    fun route(): String
}