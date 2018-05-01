package com.noisyninja.quandoopoc.instrumentation;

import com.noisyninja.quandoopoc.layers.di.QuandooComponent;
import com.noisyninja.quandoopoc.model.Customer;
import com.noisyninja.quandoopoc.view.interfaces.IMainActivity;
import com.noisyninja.quandoopoc.view.main.MainPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;

/**
 * Created by sudiptadutta on 01/05/18.
 */

public class MainPresenterTest {

    private MainPresenter mainPresenter;
    @Mock
    private
    IMainActivity iMainActivity;
    @Mock
    private
    QuandooComponent quandooComponent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mainPresenter = new MainPresenter(iMainActivity, quandooComponent);
    }


    @Test
    public void onSuccessTest() {
        ArrayList<Customer> arrayList = new ArrayList<>();
        mainPresenter.onSuccess(arrayList);
        verify(iMainActivity).setCustomers(arrayList);
    }

    @Test
    public void getCustomerTest() {
        mainPresenter.getCustomers();
        verify(quandooComponent).network();
    }

    @Test
    public void onErrorTest() {
        mainPresenter.onError(new Exception("test"));
        verify(quandooComponent).database();
    }
}
