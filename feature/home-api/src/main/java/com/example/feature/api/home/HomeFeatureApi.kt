package com.example.feature.api.home

import com.example.core.feature_api.FeatureApi

interface HomeFeatureApi: FeatureApi {

    fun homeRoute(): String

    fun homeDetails(parameter: String): String
}