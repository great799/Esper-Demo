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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

class FeatureListViewModel : BaseViewModel() {

    @Inject
    lateinit var featureListRepository: FeatureListRepository

    val uiAdapterLiveData: MutableLiveData<MutableList<FeatureListAdapterItem>> = MutableLiveData()
    val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val networkErrorLiveData: MutableLiveData<String> = MutableLiveData()
    val apiErrorLiveData: MutableLiveData<ApiResponseWrapper.GenericError> = MutableLiveData()
    val submitButtonVisibilityLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private var originalUIAdapterDataMap: HashMap<String, FeatureListAdapterItem>? = null
    private var exclusionsDataMap: HashMap<String, MutableList<String>>? = null

    private var selectedItems: LinkedList<String>? = null
    private var deSelectedItems: LinkedList<String>? = null

    init {
        EsperApplication.getAppComponent().inject(this)
    }

    fun getFeaturesData() {
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            when (val response = featureListRepository.getFeaturesData()) {
                is ApiResponseWrapper.Success -> {
                    processDataToShowInUI(response.value.features, response.value.exclusions)
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

    private suspend fun processDataToShowInUI(
        features: List<MobileFeatureDetail>,
        exclusionsData: List<List<ExclusionItem>>
    ) {
        withContext(Dispatchers.IO) {
            originalUIAdapterDataMap = LinkedHashMap()
            var originalUIAdapterData = mutableListOf<FeatureListAdapterItem>()
            for (feature in features) {
                val adapterItem = FeatureListAdapterItem().apply {
                    showTitle = true
                    title = feature.featureName
                    featureId = feature.featureId
                }
                originalUIAdapterData.add(adapterItem)
                originalUIAdapterDataMap?.put("${feature.featureId}-0", adapterItem)
                for (option in feature.options) {
                    val adapterItem = FeatureListAdapterItem().apply {
                        name = option.optionName
                        logo = option.optionIcon
                        featureId = feature.featureId
                        optionId = option.optionId
                    }
                    originalUIAdapterData.add(adapterItem)
                    originalUIAdapterDataMap?.put(
                        "${feature.featureId}-${option.optionId}",
                        adapterItem
                    )
                }
            }

            exclusionsDataMap = HashMap()
            for (item in exclusionsData) {
                for (i in item.indices) {
                    var list = exclusionsDataMap?.get("${item[i].featureId}-${item[i].optionsId}")
                    if (list == null) {
                        list = mutableListOf()
                    }
                    for (j in item.indices) {
                        if (i != j) {
                            list.add("${item[j].featureId}-${item[j].optionsId}")
                        }
                    }
                    exclusionsDataMap?.put("${item[i].featureId}-${item[i].optionsId}", list)
                }
            }

            loadingLiveData.postValue(false)
            uiAdapterLiveData.postValue(ArrayList(originalUIAdapterData))
        }
    }

    fun onFeatureCheckChanged(item: String, isChecked: Boolean) {
        if (selectedItems == null) {
            selectedItems = LinkedList()
        }
        if (deSelectedItems == null) {
            deSelectedItems = LinkedList()
        }
        if (isChecked) {
            selectedItems?.add(item)
            deSelectedItems?.remove(item)
        } else {
            deSelectedItems?.add(item)
            selectedItems?.remove(item)
        }
        submitButtonVisibilityLiveData.postValue(!selectedItems.isNullOrEmpty())
        refreshItemsOnCheckChange()
    }

    private fun refreshItemsOnCheckChange() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadingLiveData.postValue(true)
                val dataMap = LinkedHashMap(originalUIAdapterDataMap)
                for (key in selectedItems!!) {
                    val exclusions = exclusionsDataMap?.get(key)
                    if (exclusions != null) {
                        for (exclusion in exclusions!!) {
                            dataMap.remove(exclusion)
                        }
                    }
                    dataMap[key]?.isChecked = true
                }
                for (key in deSelectedItems!!) {
                    dataMap[key]?.isChecked = false
                }

                var originalUIAdapterData = mutableListOf<FeatureListAdapterItem>()
                for (key in dataMap.keys) {
                    val feature = dataMap[key]
                    originalUIAdapterData.add(feature!!)
                }

                uiAdapterLiveData.postValue(ArrayList(originalUIAdapterData))
                loadingLiveData.postValue(false)
            }
        }
    }
}