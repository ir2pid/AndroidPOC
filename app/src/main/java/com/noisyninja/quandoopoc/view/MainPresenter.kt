package com.noisyninja.quandoopoc.view

import android.content.Intent
import com.noisyninja.quandoopoc.QuandooInjector.quandooApplication
import com.noisyninja.quandoopoc.QuandooInjector.quandooComponent
import com.noisyninja.quandoopoc.layers.network.ICallback
import com.noisyninja.quandoopoc.model.Customer
import com.noisyninja.quandoopoc.view.interfaces.IMainActivity
import com.noisyninja.quandoopoc.view.interfaces.IMainPresenter
import java.io.BufferedReader
import java.io.InputStreamReader


/**
 * Created by sudiptadutta on 28/04/18.
 */

class MainPresenter internal constructor(internal var iMainActivity: IMainActivity) : IMainPresenter, ICallback<List<Customer>> {

    override fun getCustomers() {
        quandooComponent.network().getCustomers(this)
    }

    override fun openDetail(id: Int) {

        val intent = Intent(quandooApplication?.applicationContext, DetailActivity::class.java)
        intent.putExtra("customerID", id)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        quandooApplication?.applicationContext?.startActivity(intent)
    }

    override fun onSuccess(result: List<Customer>?) {
        result?.let {
            iMainActivity.setCustomers(ArrayList(result))
            quandooComponent.database().insertAll(result)
        }
    }

    override fun onError(t: Throwable) {
        //iMainActivity.setCustomers(getMock())
    }

    private fun getMock(): ArrayList<Customer> {
        val buf = StringBuilder()
        val json = quandooComponent.resources().assets.open("customer-list.json")
        val input = BufferedReader(InputStreamReader(json, "UTF-8"))

        var line = input.readLine()
        while (line != null) {
            buf.append(line)
            line = input.readLine()
        }
        input.close()

        var customerList = quandooComponent.util().fromJson<ArrayList<Customer>>(buf.toString(), ArrayList<Customer>().javaClass)
        return customerList
    }


}
