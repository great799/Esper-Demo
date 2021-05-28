package com.app.esper_demo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.esper_demo.EsperApplication
import com.app.esper_demo.R
import com.app.esper_demo.base.BaseViewModel
import com.app.esper_demo.network.model.ExclusionItem
import com.app.esper_demo.network.model.MobileFeatureDetail
import com.app.esper_demo.network.respository.FeatureListRepository
import com.app.esper_demo.ui.adapter.FeatureListAdapterItem
import com.app.esper_demo.utils.ApiResponseWrapper
import com.app.esper_demo.utils.AppLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeatureListViewModel : BaseViewModel() {

    @Inject
    lateinit var featureListRepository: FeatureListRepository

    val uiAdapterLiveData: MutableLiveData<MutableList<FeatureListAdapterItem>> = MutableLiveData()
    val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val networkErrorLiveData: MutableLiveData<String> = MutableLiveData()
    val apiErrorLiveData: MutableLiveData<ApiResponseWrapper.GenericError> = MutableLiveData()
    private var originalUIAdapterData: MutableList<FeatureListAdapterItem> = ArrayList()
    private var exclusionsData: List<List<ExclusionItem>> = ArrayList()

    init {
        EsperApplication.getAppComponent().inject(this)
    }

    fun getFeaturesData() {
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            when (val response = featureListRepository.getFeaturesData()) {
                is ApiResponseWrapper.Success -> {
                    exclusionsData = response.value.exclusions
                    processDataToShowInUI(response.value.features)
                }
                is ApiResponseWrapper.NetworkError -> {
                    loadingLiveData.postValue(false)
                    networkErrorLiveData.postValue(
                        EsperApplication.getInstance().getString(R.string.network_down)
                    )
                }
                is ApiResponseWrapper.GenericError -> {
                    loadingLiveData.postValue(false)
                    apiErrorLiveData.postValue(response)
                }
            }
        }
    }

    private suspend fun processDataToShowInUI(features: List<MobileFeatureDetail>) {
        withContext(Dispatchers.IO) {
            originalUIAdapterData.clear()
            for (feature in features) {
                val adapterItem = FeatureListAdapterItem().apply {
                    showTitle = true
                    title = feature.featureName
                    featureId = feature.featureId
                }
                originalUIAdapterData.add(adapterItem)
                for (option in feature.options) {
                    val adapterItem = FeatureListAdapterItem().apply {
                        name = option.optionName
                        logo = option.optionIcon
                        featureId = feature.featureId
                        optionId = option.optionId
                    }
                    originalUIAdapterData.add(adapterItem)
                }
            }
            loadingLiveData.postValue(false)
            uiAdapterLiveData.postValue(ArrayList(originalUIAdapterData))
        }
    }

    fun refreshItemsOnCheckChange(features: MutableList<FeatureListAdapterItem>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var count = 0
                loadingLiveData.postValue(true)
                for (item in uiAdapterLiveData.value!!) {
                    if(item.isChecked){
                        count++
                    }
                }
                AppLog.d("Checked item count: $count")
                loadingLiveData.postValue(false)
            }
        }
    }
}