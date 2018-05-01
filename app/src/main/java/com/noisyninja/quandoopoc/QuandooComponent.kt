package com.noisyninja.quandoopoc

import android.content.res.Resources
import com.noisyninja.quandoopoc.layers.UtilModule
import com.noisyninja.quandoopoc.layers.database.DataBaseModule
import com.noisyninja.quandoopoc.layers.network.NetworkModule
import dagger.Component
import javax.inject.Singleton

/**
 * module interface
 * Created by sudiptadutta on 27/04/18.
 */

@Singleton
@Component(modules = arrayOf(QuandooModule::class))
interface QuandooComponent {

    fun inject(quandooApplication: QuandooApp)
    fun app():QuandooApp
    fun resources(): Resources
    fun network(): NetworkModule
    fun database(): DataBaseModule
    fun util(): UtilModule
}