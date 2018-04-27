package com.noisyninja.quandoopoc.model;

import android.databinding.BaseObservable;

import com.noisyninja.quandoopoc.layers.Utils;

/**
 * Created by sudiptadutta on 27/04/18.
 */

public class BaseDTO extends BaseObservable {
    @Override
    public String toString() {
        return Utils.toJson(this);
    }
}

