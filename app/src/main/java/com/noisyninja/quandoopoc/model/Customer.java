package com.noisyninja.quandoopoc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sudiptadutta on 27/04/18.
 */

public class Customer extends BaseDTO {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("customerFirstName")
    @Expose
    public String firstName;

    @SerializedName("customerLastName")
    @Expose
    public String lastName;

    public Customer(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
