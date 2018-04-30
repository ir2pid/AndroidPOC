package com.noisyninja.quandoopoc.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.noisyninja.quandoopoc.R
import com.noisyninja.quandoopoc.model.Table
import com.noisyninja.quandoopoc.view.interfaces.IDetailActivity
import com.noisyninja.quandoopoc.view.interfaces.IDetailPresenter
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity(), IDetailActivity {

    private var mResultList: ArrayList<Table> = ArrayList()
    private lateinit var mIDetailPresenter: IDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val customerID = intent.getIntExtra("customerID", 0)

        mIDetailPresenter = DetailPresenter(this, customerID)
        mIDetailPresenter.getTables()
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

            //recyclerListDetail.adapter = DetailAdapter(this, mResultList, mIDetailPresenter)
            recyclerListDetail.post({
                val adapter = recyclerListDetail.adapter as DetailAdapter
                adapter.notifyDataSetChanged()
            })
            handleShowError(false, null)
        }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings ->
                return true
            else -> super.onOptionsItemSelected(item)
        }
    }

}