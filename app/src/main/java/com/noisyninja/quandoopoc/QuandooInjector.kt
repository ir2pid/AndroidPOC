package com.noisyninja.quandoopoc

import android.content.Context

/**
 * injects QuandooC0mponent to TriveApplication so it can be accessed from all activities
 * Created by sudiptadutta on 27/04/18.
 */

object QuandooInjector {

    var quandooApplication: QuandooApp? = null

    val quandooComponent: QuandooComponent
        get() = quandooApplication!!.quandooComponent!!

    fun setApplication(application: QuandooApp) {
        quandooApplication = application
    }

    fun getQuandooComponent(context: Context): QuandooComponent =
            (context.applicationContext as QuandooApp).quandooComponent!!
}