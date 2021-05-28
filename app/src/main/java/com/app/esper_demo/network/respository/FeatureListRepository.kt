package com.app.esper_demo.network.respository

import com.app.esper_demo.EsperApplication
import com.app.esper_demo.base.BaseRepository
import com.app.esper_demo.database.AppDatabase
import com.app.esper_demo.network.ServiceApi
import com.app.esper_demo.network.model.MobileFeatureApiResponse
import com.app.esper_demo.utils.ApiResponseWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import javax.inject.Inject

class FeatureListRepository @Inject constructor() : BaseRepository() {

    @Inject
    lateinit var serviceApi: ServiceApi

    @Inject
    lateinit var database: AppDatabase

    init {
        EsperApplication.getAppComponent().inject(this)
    }

    suspend fun getFeaturesData(): ApiResponseWrapper<MobileFeatureApiResponse> {
        val response = safeApiCall(Dispatchers.IO) {
            serviceApi.getFeatureData()
        }
//        when (response) {
//            is ApiResponseWrapper.NetworkError -> {
//                return getFeaturesDataFromDB()
//            }
//            is ApiResponseWrapper.Success -> {
//                coroutineScope {
//                    async(Dispatchers.IO) {
//                        delay(10000)
////                        updateDatabase(response.value)
//                    }
//                }
//            }
//        }
        return response
    }

//    private suspend fun getFeaturesDataFromDB(): ApiResponseWrapper<MobileFeatureApiResponse> {
//        return ApiResponseWrapper.Success(
//            MobileFeatureApiResponse(
//                database.featureDao().getAll(),
//                database.exclusionDao().getAll()
//            )
//        )
//    }

    private suspend fun updateDatabase(apiResponse: MobileFeatureApiResponse) {
        database.featureDao().nukeTable()
        database.featureDao().insertListOfFeatures(apiResponse.features)

        database.exclusionDao().nukeTable()
//        database.exclusionDao().insertExclusions(apiResponse.exclusions)
    }
}