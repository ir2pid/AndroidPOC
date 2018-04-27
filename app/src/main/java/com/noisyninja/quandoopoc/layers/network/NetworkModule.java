package com.noisyninja.quandoopoc.layers.network;

import com.noisyninja.quandoopoc.model.Customer;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * network module to make network calls
 * Created by sudiptadutta on 27/04/18.
 */

public class NetworkModule {

    private HttpClient mHttpClient;

    public NetworkModule(HttpClient httpClient) {
        mHttpClient = httpClient;
    }

    public void getCustomers(final ICallback iCallback) {
        getMovieObservable().subscribeWith(getObserver(iCallback));
    }


    private Observable<Customer> getMovieObservable() {
        return mHttpClient.getClient().create(IResturant.class)
                .getCustomers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private <T> GenericObserver<T> getObserver(final ICallback iCallback) {
        return new GenericObserver<>(iCallback);
    }
}
