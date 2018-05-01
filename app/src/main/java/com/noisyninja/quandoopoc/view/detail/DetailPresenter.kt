package com.noisyninja.quandoopoc.view.detail

import android.view.View
import com.noisyninja.quandoopoc.QuandooInjector.quandooComponent
import com.noisyninja.quandoopoc.R
import com.noisyninja.quandoopoc.layers.network.ICallback
import com.noisyninja.quandoopoc.model.Table
import com.noisyninja.quandoopoc.view.interfaces.IDetailActivity
import com.noisyninja.quandoopoc.view.interfaces.IDetailPresenter

/**
 * Created by sudiptadutta on 28/04/18.
 */

class DetailPresenter internal constructor(private val iDetailActivity: IDetailActivity, private val customerID: Int) : IDetailPresenter, ICallback<List<Boolean>> {

    override fun setBookMarked(view: View?, table: Table) {
        if ((table.isOccupied && table.customerID == -1)
                || (table.customerID != -1 && table.customerID != customerID)) {
            quandooComponent.util().logI(DetailPresenter::class.java, "already booked from server, or another reservation")
            quandooComponent.util().toast(view?.context, quandooComponent.resources().getString(R.string.error_book))
            return
        }
        table.isOccupied = !table.isOccupied
        table.customerID = if (table.isOccupied) customerID else -1
        quandooComponent.database().insertTable(table)
        quandooComponent.util().logI(DetailPresenter::class.java, "table toggled")
        iDetailActivity.refresh()
    }

    override fun getTables() {

        quandooComponent.database().allTable
                .subscribe { list: List<Table> ->
                    if (list.isEmpty()) {//call only once
                        quandooComponent.network().getTables(this)
                        quandooComponent.util().logI(DetailActivity::class.java, "web call for once" + list.toString())
                    } else {
                        iDetailActivity.setTables(ArrayList(list))
                        quandooComponent.util().logI(DetailActivity::class.java, "local" + list.toString())
                    }
                }
    }

    override fun onSuccess(result: List<Boolean>?) {
        result?.let {
            val arr = ArrayList<Table>()
            for (b in result) {
                arr.add(Table(b, -1))
            }
            quandooComponent.database().insertAllTable(arr)
            iDetailActivity.setTables(arr)
        }
    }

    override fun onError(t: Throwable) {
        quandooComponent.database().allTable.subscribe { list: List<Table> ->
            if (list.isEmpty()) {
                quandooComponent.util().logI(DetailPresenter::class.java, "no local cache")
                iDetailActivity.setTables(null)
            } else {
                quandooComponent.util().logI(DetailPresenter::class.java, "got local cache")
                iDetailActivity.setTables(ArrayList(list))
            }
        }
    }

}