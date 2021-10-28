package com.example.api.onboarding

import com.example.core.feature_api.FeatureApi

interface OnboardingFeatureApi: FeatureApi {

    fun route(): String
}