package com.app.esper_demo.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.esper_demo.R
import com.app.esper_demo.base.BaseFragment
import com.app.esper_demo.databinding.FragmentFeatureListBinding
import com.app.esper_demo.ui.adapter.FeatureListAdapter
import com.app.esper_demo.ui.adapter.FeatureListAdapterItem
import com.app.esper_demo.utils.AppViewModelScope
import com.app.esper_demo.viewmodel.FeatureListViewModel

class FeatureListFragment : BaseFragment(), FeatureListAdapter.ItemSelectedListener {
    override fun getLayoutId(): Int {
        return R.layout.fragment_feature_list
    }

    override fun getViewModelClass(): Class<out ViewModel> {
        return FeatureListViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory? {
        return null
    }

    override fun getViewModelScope(): AppViewModelScope {
        return AppViewModelScope.FRAGMENT
    }

    companion object {
        fun getNewInstance(): FeatureListFragment {
            return FeatureListFragment()
        }
    }

    private lateinit var binding: FragmentFeatureListBinding
    private lateinit var viewModel: FeatureListViewModel
    private var featureListAdapter: FeatureListAdapter? = null
    private var features: MutableList<FeatureListAdapterItem>? = null

    private fun initBindingAndViewModel() {
        binding = getBinding() as FragmentFeatureListBinding
        viewModel = getViewModel() as FeatureListViewModel
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindingAndViewModel()
        initUI()
        initObservers()
    }

    private fun initUI() {
        binding.btnSubmit.visibility = View.GONE
        viewModel.getFeaturesData()
    }

    private fun initObservers() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner, {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        })

        viewModel.apiErrorLiveData.observe(viewLifecycleOwner, {
            handleApiError(it.code, it.errorMessage)
        })

        viewModel.networkErrorLiveData.observe(viewLifecycleOwner, {
            showToast(it, Toast.LENGTH_LONG)
        })

        viewModel.submitButtonVisibilityLiveData.observe(viewLifecycleOwner, {
            if (it) {
                binding.btnSubmit.visibility = View.VISIBLE
            } else {
                binding.btnSubmit.visibility = View.GONE
            }
        })

        viewModel.uiAdapterLiveData.observe(viewLifecycleOwner, {
            if (features == null) {
                features = it
            } else {
                features?.clear()
                features?.addAll(it)
            }
            setAdapter()
        })
    }

    private fun setAdapter() {
        if (featureListAdapter == null) {
            binding.rvFeatures.layoutManager = LinearLayoutManager(activity)
            featureListAdapter = FeatureListAdapter(features!!)
            featureListAdapter?.setItemSelectedListener(this)
            binding.rvFeatures.adapter = featureListAdapter
        } else {
            featureListAdapter?.notifyDataSetChanged()
        }
    }

    override fun onItemSelect(featureId: String, optionId: String, isChecked: Boolean) {
        viewModel.onFeatureCheckChanged("$featureId-$optionId", isChecked)
    }
}