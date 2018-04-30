package com.noisyninja.quandoopoc.view.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.noisyninja.quandoopoc.QuandooInjector.quandooComponent
import com.noisyninja.quandoopoc.R
import com.noisyninja.quandoopoc.model.Table
import com.noisyninja.quandoopoc.view.interfaces.IDetailActivity
import com.noisyninja.quandoopoc.view.interfaces.IDetailPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity(), IDetailActivity {

    private var mResultList: ArrayList<Table> = ArrayList()
    private lateinit var mIDetailPresenter: IDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val customerID = intent.getIntExtra("customerID", 0)

        mIDetailPresenter = DetailPresenter(this, customerID)

        setupUI()
    }

    private fun setupUI() {
        //region UI
        recyclerListDetail.layoutManager = GridLayoutManager(this, 4)
        recyclerListDetail.adapter = DetailAdapter(this, mResultList, mIDetailPresenter)
        recyclerListDetail.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
        recyclerListDetail.addItemDecoration(DividerItemDecoration(this, LinearLayout.HORIZONTAL))
    }


    override fun setTables(result: ArrayList<Boolean>?) {
        mResultList.clear()
        result?.let {
            for (b in result) {
                mResultList.add(Table(b, -1))
            }
            quandooComponent.database().insertAllTable(mResultList)
            //recyclerListDetail.adapter = DetailAdapter(this, mResultList, mIDetailPresenter)
            updateTables()
        }
    }

    private fun updateTables() {
        recyclerListDetail.post({
            val adapter = recyclerListDetail.adapter as DetailAdapter
            adapter.notifyDataSetChanged()
        })
        handleShowError(false, null)
    }

    override fun refresh() {
        recyclerListDetail.adapter.notifyDataSetChanged()
    }

    /**
     * show an error message if loading fails
     */
    private fun handleShowError(isError: Boolean, t: Throwable?) {
        if (isError) {
            recyclerListDetail.visibility = View.GONE
            recyclerTextDetail.visibility = View.VISIBLE
            recyclerTextDetail.text = t?.message
        } else {
            recyclerListDetail.visibility = View.VISIBLE
            recyclerTextDetail.visibility = View.GONE
        }
    }

    public fun done(view: View) {
        quandooComponent.util().logI(DetailActivity::class.java, "done")
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        quandooComponent.database().allTable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe { list: List<Table> ->
                    if (list.isEmpty()) {//call only once
                        mIDetailPresenter.getTables()
                        quandooComponent.util().logI(DetailActivity::class.java, "web" + list.toString())
                    } else {
                        mResultList.clear()
                        mResultList.addAll(list)
                        updateTables()
                        quandooComponent.util().logI(DetailActivity::class.java, "local" + list.toString())
                    }
                }
    }
}