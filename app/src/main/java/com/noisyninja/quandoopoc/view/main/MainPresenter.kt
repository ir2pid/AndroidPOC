package com.noisyninja.quandoopoc.view.main

import android.content.Intent
import com.noisyninja.quandoopoc.QuandooInjector.quandooApplication
import com.noisyninja.quandoopoc.QuandooInjector.quandooComponent
import com.noisyninja.quandoopoc.layers.network.ICallback
import com.noisyninja.quandoopoc.model.Customer
import com.noisyninja.quandoopoc.view.custom.BaseActivity
import com.noisyninja.quandoopoc.view.detail.DetailActivity
import com.noisyninja.quandoopoc.view.interfaces.IMainActivity
import com.noisyninja.quandoopoc.view.interfaces.IMainPresenter


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
        if (result == null) {
            quandooComponent.util().logI(BaseActivity::class.java, "null response")
            iMainActivity.setCustomers(null)
        } else {
            quandooComponent.util().logI(BaseActivity::class.java, "got response")
            iMainActivity.setCustomers(ArrayList(result))
            quandooComponent.database().insertAll(result)
        }
    }

    override fun onError(t: Throwable) {
        quandooComponent.database().all.subscribe { list: List<Customer> ->
            if (list.isEmpty()) {
                quandooComponent.util().logI(BaseActivity::class.java, "no local cache")
                iMainActivity.setCustomers(null)
            } else {
                quandooComponent.util().logI(BaseActivity::class.java, "got local cache")
                iMainActivity.setCustomers(ArrayList(list))
            }
        }
    }

}
