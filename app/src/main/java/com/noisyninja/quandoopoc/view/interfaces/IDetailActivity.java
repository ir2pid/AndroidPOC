package com.noisyninja.quandoopoc.view.interfaces;

import com.noisyninja.quandoopoc.model.Table;

import java.util.ArrayList;

/**
 * Created by sudiptadutta on 28/04/18.
 */
public interface IDetailActivity {

    void setTables(ArrayList<Table> result);
    void refresh();
}
