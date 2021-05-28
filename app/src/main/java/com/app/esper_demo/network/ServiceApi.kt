package com.app.esper_demo.network

import com.app.esper_demo.network.model.MobileFeatureApiResponse
import retrofit2.http.GET

interface ServiceApi {
    @GET("mhrpatel12/esper-assignment/db")
    suspend fun getFeatureData(): MobileFeatureApiResponse
}