package com.app.esper_demo.di.module

import com.app.esper_demo.EsperApplication
import com.app.esper_demo.utils.ImageUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: EsperApplication) {

    @Singleton
    @Provides
    internal fun providesApplication(): EsperApplication {
        return application
    }

    @Singleton
    @Provides
    internal fun providesImageUtils(): ImageUtils {
        return ImageUtils()
    }
}