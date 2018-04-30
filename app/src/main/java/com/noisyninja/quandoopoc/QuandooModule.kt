package com.noisyninja.quandoopoc

import android.content.res.Resources
import com.noisyninja.quandoopoc.layers.UtilModule
import com.noisyninja.quandoopoc.layers.database.DataBaseModule
import com.noisyninja.quandoopoc.layers.network.HttpClient
import com.noisyninja.quandoopoc.layers.network.NetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * module containing dependency modules
 * Created by sudiptadutta on 27/04/18.
 */

@Module
class QuandooModule(private val application: QuandooApp) {

    @Provides
    @Singleton
    fun provideApplication() = application

    @Provides
    @Singleton
    fun provideResources(): Resources {
        return application.resources
    }

    @Provides
    @Singleton
    fun provideHttpClientT(): HttpClient {
        return HttpClient()
    }

    @Provides
    @Singleton
    fun provideNetwork(httpClient: HttpClient): NetworkModule {
        return NetworkModule(httpClient)
    }


        @Provides
        @Singleton
        fun provideDataBase(utilModule: UtilModule, application: QuandooApp): DataBaseModule {
            return DataBaseModule(utilModule, application)
        }

    @Provides
    @Singleton
    fun provideUtil(): UtilModule {
        return UtilModule()
    }
}