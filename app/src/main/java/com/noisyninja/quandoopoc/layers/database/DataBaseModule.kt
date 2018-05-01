package com.noisyninja.quandoopoc.layers.database

import android.arch.persistence.room.Room
import android.content.Context
import com.noisyninja.quandoopoc.BuildConfig
import com.noisyninja.quandoopoc.layers.UtilModule
import com.noisyninja.quandoopoc.model.Customer
import com.noisyninja.quandoopoc.model.Table
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

/**
 * databse client
 * Created by sudiptadutta on 30/04/18.
 */

class DataBaseModule(internal var mUtilModule: UtilModule, context: Context) : DatabaseDao {

    internal var mDataBase: IDatabase

    init {
        mDataBase = Room.databaseBuilder(context,
                IDatabase::class.java, BuildConfig.APP_DB).build()
    }

    override fun getAll(): Flowable<List<Customer>> {
        return mDataBase.databaseDao().all
    }

    override fun findById(id: Int): Single<Customer> {

        return mDataBase.databaseDao().findById(id)
    }

    override fun delete(results: Customer) {
        // Completable.fromAction { mDataBase.databaseDao.delete(results) } //non verbose short hand

        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                mDataBase.databaseDao().delete(results)
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {}

            override fun onComplete() {
                mUtilModule.logI(DataBaseModule::class.java, "delete done")
            }

            override fun onError(e: Throwable) {
                mUtilModule.logI(DataBaseModule::class.java, "delete error")
            }
        })
    }

    override fun insert(results: Customer) {
        // Completable.fromAction { mDataBase.databaseDao().insert(results) } //non verbose short hand

        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                mDataBase.databaseDao().insert(results)
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {}

            override fun onComplete() {
                mUtilModule.logI(DataBaseModule::class.java, "insert done")
            }

            override fun onError(e: Throwable) {
                mUtilModule.logI(DataBaseModule::class.java, "insert error")
            }
        })
    }

    override fun insertAll(customers: List<Customer>?) {
        // Completable.fromAction { mDataBase.databaseDao().insertAll(customers) } //non verbose short han
        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                mUtilModule.logI(DataBaseModule::class.java, "inserting")
                mDataBase.databaseDao().insertAll(customers)
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {}

            override fun onComplete() {
                mUtilModule.logI(DataBaseModule::class.java, "insert done")
            }

            override fun onError(e: Throwable) {
                mUtilModule.logI(DataBaseModule::class.java, "insert error")
            }
        })
    }

    override fun getAllTable(): Single<MutableList<Table>> {
        return mDataBase.databaseDao().allTable
    }

    override fun findTablesByCustomerID(customerID: Int): Flowable<MutableList<Table>> {
        return mDataBase.databaseDao().findTablesByCustomerID(customerID)
    }

    override fun findTableByID(id: Int): Single<Table> {
        return mDataBase.databaseDao().findTableByID(id)
    }

    override fun insertTable(table: Table?) {

        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                mDataBase.databaseDao().insertTable(table)
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {}

            override fun onComplete() {
                mUtilModule.logI(DataBaseModule::class.java, "insert table done")
            }

            override fun onError(e: Throwable) {
                mUtilModule.logI(DataBaseModule::class.java, "insert table error")
            }
        })
    }

    override fun deleteTable(table: Table?) {
        Completable.fromAction { mDataBase.databaseDao().deleteTable(table) } //non verbose short hand
    }

    override fun insertAllTable(tableList: List<Table>?) {
        mUtilModule.logI(DataBaseModule::class.java, "inserting all table")
        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                mUtilModule.logI(DataBaseModule::class.java, "inserting table")
                mDataBase.databaseDao().insertAllTable(tableList)
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {}

            override fun onComplete() {
                mUtilModule.logI(DataBaseModule::class.java, "insert AllTable done")
            }

            override fun onError(e: Throwable) {
                mUtilModule.logI(DataBaseModule::class.java, "insert AllTable error")
            }
        })
    }

}