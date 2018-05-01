package com.noisyninja.quandoopoc.junit

import com.noisyninja.quandoopoc.layers.Utils
import com.noisyninja.quandoopoc.model.Table
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {

    lateinit var table: Table
    @Before
    fun setup() {
        table = Table(true, 100)
    }

    @Test
    fun marshall() {
        var json = Utils.toJson(table)
        Assert.assertNotNull(json)
    }

    @Test
    fun unmarshall() {
        var json = Utils.toJson(table)
        Assert.assertEquals(table, Utils.fromJson(json, Table::class.java))
    }
}