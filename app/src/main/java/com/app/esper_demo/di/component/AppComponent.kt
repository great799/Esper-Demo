package com.app.esper_demo.di.component

import com.app.esper_demo.di.module.AppDatabaseModule
import com.app.esper_demo.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class, AppDatabaseModule::class]
)
interface AppComponent {
//    fun inject(mainActivity: MainActivity)
//    fun inject(mainRepository: MainRepository)
}