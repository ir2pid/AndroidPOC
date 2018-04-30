package com.noisyninja.quandoopoc

import android.app.Application

/**
 * Created by sudiptadutta on 27/04/18.
 */

class QuandooApp : Application() {

    var quandooComponent: QuandooComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()

        quandooComponent = DaggerQuandooComponent.builder()
                .quandooModule(QuandooModule(this))
                .build()
        //quandooComponent!!.inject(this)
        QuandooInjector.setApplication(this)
        QuandooInjector.getQuandooComponent(this).inject(this)
    }
}