package com.example.feature.home.api

import com.example.core.feature_api.FeatureApi

interface HomeFeatureApi: FeatureApi {

    fun homeRoute(): String
}