package com.app.esper_demo

import android.app.Application
import com.app.esper_demo.di.component.AppComponent
import com.app.esper_demo.di.component.DaggerAppComponent
import com.app.esper_demo.di.module.AppDatabaseModule
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class EsperApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: AndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    companion object {
        private var appComponent: AppComponent? = null
        private var instance: EsperApplication? = null

        fun getAppComponent(): AppComponent {
            return appComponent!!
        }

        fun getInstance(): EsperApplication {
            return instance ?: EsperApplication()
        }
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent =
            DaggerAppComponent.builder().appDatabaseModule(AppDatabaseModule(this)).build()
    }
}