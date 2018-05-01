package com.noisyninja.quandoopoc.instrumentation;

import com.noisyninja.quandoopoc.layers.di.QuandooComponent;
import com.noisyninja.quandoopoc.model.Table;
import com.noisyninja.quandoopoc.view.detail.DetailPresenter;
import com.noisyninja.quandoopoc.view.interfaces.IDetailActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;

/**
 * Created by sudiptadutta on 01/05/18.
 */

public class DetailPresenterTest {

    private DetailPresenter detailPresenter;
    @Mock
    private
    IDetailActivity iDetailActivity;
    @Mock
    private
    QuandooComponent quandooComponent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        detailPresenter = new DetailPresenter(iDetailActivity, quandooComponent, 1);
    }


    @Test
    public void onSuccessTest() {
        ArrayList<Boolean> arrayList = new ArrayList<>();
        detailPresenter.onSuccess(arrayList);
        verify(iDetailActivity).setTables(Matchers.<ArrayList<Table>>any());
    }

    @Test
    public void getTablesTest() {
        detailPresenter.getTables();
        verify(quandooComponent).database();
    }

    @Test
    public void onErrorTest() {
        detailPresenter.onError(new Exception("test"));
        verify(quandooComponent).database();
    }
}
