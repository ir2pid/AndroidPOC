package com.noisyninja.quandoopoc

import android.app.Application

/**
 * Created by sudiptadutta on 27/04/18.
 */

class QuandooApp : Application() {

    var triveComponent: QuandooComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()

        triveComponent = DaggerQuandooComponent.builder()
                .quandooModule(QuandooModule(this))
                .build()

        triveComponent!!.inject(this)
        //TriveInjector.getTriveComponent(this).inject(this)
        //TriveInjector.setApplication(this)
    }
}