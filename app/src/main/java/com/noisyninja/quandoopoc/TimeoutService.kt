package com.noisyninja.quandoopoc

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.noisyninja.quandoopoc.QuandooInjector.quandooComponent
import com.noisyninja.quandoopoc.layers.Utils
import com.noisyninja.quandoopoc.model.Table
import java.util.*

/**
 * Created by sudiptadutta on 01/05/18.
 */

class TimeoutService : Service() {
    internal lateinit var context: Context

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        timer.scheduleAtFixedRate(Task(), java.lang.Long.valueOf(BuildConfig.TIMER), java.lang.Long.valueOf(BuildConfig.TIMER)!!)

        Utils.logI(TimeoutService::class.java, "Service started ...")
        Utils.logI(TimeoutService::class.java, "Timer started ...")
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        Utils.logI(TimeoutService::class.java, "Service stopped ...")
        Utils.logI(TimeoutService::class.java, "Timer stopped ...")
    }

    private inner class Task : TimerTask() {
        override fun run() {
            Utils.logI(TimeoutService::class.java, "reset reservations!")

            QuandooInjector.quandooComponent.util().logI(TimeoutService::class.java, "resetting reservations timeout")
            QuandooInjector.quandooComponent.database().allTable.subscribe { list: List<Table> ->
                if (!list.isEmpty()) {
                    for (table: Table in list) {
                        table.isOccupied = false
                        table.customerID = -1
                    }

                    quandooComponent.database().insertAllTable(list)
                }
            }

            val intent = Intent()
            intent.action = BuildConfig.APPLICATION_ID
            sendBroadcast(intent)
        }
    }

    companion object {
        private val timer = Timer()
    }
    /*
    private static final Handler toastHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
        }
    };*/
}