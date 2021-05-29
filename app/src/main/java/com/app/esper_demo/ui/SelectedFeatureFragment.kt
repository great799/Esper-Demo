package com.app.esper_demo.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.esper_demo.R
import com.app.esper_demo.base.BaseFragment
import com.app.esper_demo.databinding.FragmentSelectedFeatureBinding
import com.app.esper_demo.ui.adapter.FeatureListAdapterItem
import com.app.esper_demo.utils.AppViewModelScope
import com.app.esper_demo.viewmodel.SelectedFeatureViewModel

class SelectedFeatureFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_selected_feature
    }

    override fun getViewModelClass(): Class<out ViewModel> {
        return SelectedFeatureViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory? {
        return null
    }

    override fun getViewModelScope(): AppViewModelScope {
        return AppViewModelScope.FRAGMENT
    }

    companion object {
        const val SELECTED_FEATURES = "selected_features"
        fun getNewInstance(selectedFeatures: ArrayList<FeatureListAdapterItem>): SelectedFeatureFragment {
            return SelectedFeatureFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(SELECTED_FEATURES, selectedFeatures)
                }
            }
        }
    }

    private lateinit var binding: FragmentSelectedFeatureBinding
    private lateinit var viewModel: SelectedFeatureViewModel
    private var selectedFeatures: ArrayList<FeatureListAdapterItem>? = null

    private fun initBindingAndViewModel() {
        binding = getBinding() as FragmentSelectedFeatureBinding
        viewModel = getViewModel() as SelectedFeatureViewModel
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedFeatures = arguments?.getParcelableArrayList(SELECTED_FEATURES)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindingAndViewModel()
        initUI()
    }

    private fun initUI() {
        var displayText = ""
        if (selectedFeatures != null) {
            for (item in selectedFeatures!!) {
                displayText += "${item.name} \n\n"
            }
        }
        binding.txtSelectedFeature.text = displayText
    }
}