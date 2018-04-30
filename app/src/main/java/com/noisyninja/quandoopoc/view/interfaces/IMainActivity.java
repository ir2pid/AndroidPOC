package com.noisyninja.quandoopoc.view.interfaces;

import com.noisyninja.quandoopoc.model.Customer;

import java.util.ArrayList;

/**
 * Created by sudiptadutta on 28/04/18.
 */
public interface IMainActivity {

    void setCustomers(ArrayList<Customer> result);
    void openDetail(int id);
}
