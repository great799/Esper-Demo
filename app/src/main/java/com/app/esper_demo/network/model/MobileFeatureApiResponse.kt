package com.app.esper_demo.network.model

import com.squareup.moshi.Json

/*
* Main api response model
* */
data class MobileFeatureApiResponse(
    @field:Json(name = "features") val features: List<MobileFeatureDetail>,
    @field:Json(name = "exclusions") val exclusions: List<List<ExclusionItem>>
)
