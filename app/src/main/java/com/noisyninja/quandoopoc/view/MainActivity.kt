package com.noisyninja.quandoopoc.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout.VERTICAL
import com.noisyninja.quandoopoc.QuandooInjector.quandooComponent
import com.noisyninja.quandoopoc.R
import com.noisyninja.quandoopoc.model.Customer
import com.noisyninja.quandoopoc.view.interfaces.IMainActivity
import com.noisyninja.quandoopoc.view.interfaces.IMainPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), IMainActivity {

    private var mResultList: ArrayList<Customer> = ArrayList()
    private lateinit var mIMainPresenter: IMainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mIMainPresenter = MainPresenter(this)
        setupList()
        mIMainPresenter.getCustomers()
    }

    //region UI
    private fun setupList() {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerList.layoutManager = linearLayoutManager
        recyclerList.adapter = MainAdapter(mResultList, mIMainPresenter)
        recyclerList.addItemDecoration(DividerItemDecoration(this, VERTICAL))
        refresh_layout.setOnRefreshListener {
            mIMainPresenter.getCustomers()
        }
    }

    override fun setCustomers(result: ArrayList<Customer>) {
        mResultList.clear()
        mResultList.addAll(result)
        recyclerList.post({ recyclerList.adapter.notifyDataSetChanged() })
        handleShowError(false, null)
    }

    override fun openDetail(id: Int) {
        val i = Intent(this, DetailActivity::class.java)
        startActivityForResult(i, 1)
    }

    /**
     * show an error message if loading fails
     */
    private fun handleShowError(isError: Boolean, t: Throwable?) {

        refresh_layout.isRefreshing = false

        if (isError) {
            recyclerList.visibility = GONE
            recyclerText.visibility = VISIBLE
            recyclerText.text = t?.message
        } else {
            recyclerList.visibility = VISIBLE
            recyclerText.visibility = GONE
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

    override fun onResume() {
        super.onResume()
        quandooComponent.database().all.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe { list: List<Customer> ->
                    quandooComponent.util().logI(DetailActivity::class.java, list.toString())
                }
    }
}
