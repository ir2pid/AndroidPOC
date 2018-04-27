package com.noisyninja.quandoopoc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sudiptadutta on 27/04/18.
 */

public class Table extends BaseDTO {

    @SerializedName("isOccupied")
    @Expose
    public boolean isOccupied;

}
