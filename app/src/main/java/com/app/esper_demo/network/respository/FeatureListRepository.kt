package com.app.esper_demo.network.respository

import com.app.esper_demo.EsperApplication
import com.app.esper_demo.base.BaseRepository
import com.app.esper_demo.database.AppDatabase
import com.app.esper_demo.network.ServiceApi
import com.app.esper_demo.network.model.ExclusionDetail
import com.app.esper_demo.network.model.ExclusionItem
import com.app.esper_demo.network.model.MobileFeatureApiResponse
import com.app.esper_demo.utils.ApiResponseWrapper
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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
        when (response) {
            is ApiResponseWrapper.NetworkError -> {
                return getFeaturesDataFromDB()
            }
            is ApiResponseWrapper.Success -> {
                CoroutineScope(Dispatchers.IO).async {
                    updateDatabase(response.value)
                }
                return response
            }
        }
        return response
    }

    private suspend fun getFeaturesDataFromDB(): ApiResponseWrapper<MobileFeatureApiResponse> {
        return coroutineScope {
            withContext(Dispatchers.IO) {
                ApiResponseWrapper.Success(
                    MobileFeatureApiResponse(
                        database.featureDao().getAll(),
                        getListOfExclusionItem(database.exclusionDao().getAll())
                    )
                )
            }
        }
    }

    private suspend fun getListOfExclusionItem(input: List<ExclusionDetail>): List<List<ExclusionItem>> {
        val output = mutableListOf<List<ExclusionItem>>()
        for (item in input) {
            output.add(item.exclusionsDetail)
        }
        return output
    }

    private suspend fun getListOfExclusionDetail(input: List<List<ExclusionItem>>): List<ExclusionDetail> {
        val output = mutableListOf<ExclusionDetail>()
        for (item in input) {
            val exclusionDetail = ExclusionDetail(0, item)
            output.add(exclusionDetail)
        }
        return output
    }

    private suspend fun updateDatabase(apiResponse: MobileFeatureApiResponse) {
        database.featureDao().nukeTable()
        database.featureDao().insertListOfFeatures(apiResponse.features)

        database.exclusionDao().nukeTable()
        database.exclusionDao().insertExclusions(getListOfExclusionDetail(apiResponse.exclusions))
    }
}