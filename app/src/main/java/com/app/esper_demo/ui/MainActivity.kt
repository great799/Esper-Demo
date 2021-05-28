package com.app.esper_demo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.esper_demo.R
import com.app.esper_demo.base.BaseActivity
import com.app.esper_demo.databinding.ActivityMainBinding
import com.app.esper_demo.viewmodel.MainActivityViewModel

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModelClass(): Class<out ViewModel> {
        return MainActivityViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory? {
        return null
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBindingAndViewModel()
        initUI()
        /*
        * Do not add to back stack as it is first fragment
        * */
        if (savedInstanceState == null) {
            addFragment(
                FeatureListFragment.getNewInstance(),
                false,
                FeatureListFragment::class.java.simpleName
            )
        }
    }

    private fun initUI() {
        setUpActionBar()
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.elevation = 0f

        supportActionBar?.let {
            it.show()
            it.setDisplayHomeAsUpEnabled(false)
            it.setDisplayShowHomeEnabled(true)
            it.setHomeButtonEnabled(true)
        }
    }

    private fun initBindingAndViewModel() {
        binding = getBinding() as ActivityMainBinding
        viewModel = getViewModel() as MainActivityViewModel
        binding.viewModel = viewModel
    }

    override fun addFragment(fragment: Fragment, addToBackStack: Boolean, tag: String) {
        var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(binding.mainFrame.id, fragment, tag)
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }

    override fun replaceFragment(fragment: Fragment, addToBackStack: Boolean, tag: String) {
        var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.mainFrame.id, fragment, tag)
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }
}