package com.noisyninja.quandoopoc.view

import android.view.View
import com.noisyninja.quandoopoc.QuandooInjector.quandooComponent
import com.noisyninja.quandoopoc.layers.network.ICallback
import com.noisyninja.quandoopoc.model.Table
import com.noisyninja.quandoopoc.view.interfaces.IDetailActivity
import com.noisyninja.quandoopoc.view.interfaces.IDetailPresenter

/**
 * Created by sudiptadutta on 28/04/18.
 */

class DetailPresenter internal constructor(private val iDetailActivity: IDetailActivity, private val customerID: Int) : IDetailPresenter, ICallback<List<Boolean>> {

    override fun setBookMarked(view: View?, table: Table) {
        table.isOccupied = !table.isOccupied
        table.customerID = if (table.isOccupied) customerID else -1

        quandooComponent.util().logI(DetailPresenter::class.java, "detail")
        iDetailActivity.refresh()
    }

    override fun getTables() {
        quandooComponent.network().getTables(this)
    }

    override fun onSuccess(result: List<Boolean>?) {
        result?.let {
            iDetailActivity.setTables(ArrayList(result))
        }
    }

    override fun onError(t: Throwable) {
        //iMainActivity.setCustomers(getMock())
    }

}