package com.noisyninja.quandoopoc.layers.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.noisyninja.quandoopoc.model.Customer;
import com.noisyninja.quandoopoc.model.Table;

/**
 * Created by sudiptadutta on 30/04/18.
 */

@Database(entities = {Customer.class, Table.class}, version = 1)
public abstract class IDatabase extends RoomDatabase {
    public abstract DatabaseDao databaseDao();
}
