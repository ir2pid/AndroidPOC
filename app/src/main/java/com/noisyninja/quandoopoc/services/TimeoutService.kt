package com.noisyninja.quandoopoc.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.noisyninja.quandoopoc.BuildConfig
import com.noisyninja.quandoopoc.layers.Utils
import com.noisyninja.quandoopoc.layers.di.QuandooInjector
import com.noisyninja.quandoopoc.layers.di.QuandooInjector.quandooComponent
import com.noisyninja.quandoopoc.model.Table
import java.util.*

/**
 * Created by sudiptadutta on 01/05/18.
 */

class TimeoutService : Service() {

    internal lateinit var context: Context
    private var timer: Timer? = null

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        // cancel if already existed
        if (timer != null) {
            timer?.cancel()
        } else {
            // recreate new
            timer = Timer()
        }
        // schedule task
        timer?.scheduleAtFixedRate(Task(), 0, BuildConfig.TIMER.toLong())

        Utils.logI(TimeoutService::class.java, "Service started ...")
        Utils.logI(TimeoutService::class.java, "Timer started ...")
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        Utils.logI(TimeoutService::class.java, "Service stopped ...")
        Utils.logI(TimeoutService::class.java, "Timer stopped ...")
    }

    private inner class Task : TimerTask() {
        override fun run() {

            QuandooInjector.quandooComponent.util().logI(TimeoutService::class.java, "resetting reservations timeout")
            QuandooInjector.quandooComponent.database().allTable.subscribe { list: List<Table> ->
                if (!list.isEmpty()) {
                    for (table: Table in list) {
                        table.isOccupied = false
                        table.customerID = -1
                    }

                    quandooComponent.database().insertAllTable(list)

                    val intent = Intent()
                    intent.action = BuildConfig.APPLICATION_ID
                    sendBroadcast(intent)
                }
            }

        }
    }

}