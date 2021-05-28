package com.app.esper_demo.di.component

import com.app.esper_demo.di.module.AppDatabaseModule
import com.app.esper_demo.di.module.AppModule
import com.app.esper_demo.di.module.NetworkModule
import com.app.esper_demo.network.respository.FeatureListRepository
import com.app.esper_demo.ui.adapter.FeatureListAdapter
import com.app.esper_demo.viewmodel.FeatureListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class, AppDatabaseModule::class, AppModule::class]
)
interface AppComponent {
    fun inject(featureListRepository: FeatureListRepository)
    fun inject(featureListViewModel: FeatureListViewModel)
    fun inject(featureListAdapter: FeatureListAdapter)
}