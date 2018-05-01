package com.noisyninja.quandoopoc

import android.app.Application
import android.content.Intent
import com.noisyninja.quandoopoc.layers.di.DaggerQuandooComponent
import com.noisyninja.quandoopoc.layers.di.QuandooComponent
import com.noisyninja.quandoopoc.layers.di.QuandooInjector
import com.noisyninja.quandoopoc.layers.di.QuandooModule


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

        val intent = Intent(this, TimeoutService::class.java)
        startService(intent)
    }
}