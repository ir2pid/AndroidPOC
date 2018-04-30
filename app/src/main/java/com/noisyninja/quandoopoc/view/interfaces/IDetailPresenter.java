package com.noisyninja.quandoopoc.view.interfaces;

import android.view.View;

import com.noisyninja.quandoopoc.model.Table;

/**
 * Created by sudiptadutta on 28/04/18.
 */

public interface IDetailPresenter {
    void getTables();

    void setBookMarked(View view, Table table);
}
