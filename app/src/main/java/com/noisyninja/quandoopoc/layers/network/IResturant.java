package com.noisyninja.quandoopoc.layers.network;

import com.noisyninja.quandoopoc.BuildConfig;
import com.noisyninja.quandoopoc.model.Customer;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * retrofit interface for moviedb calls
 * Created by sudiptadutta on 27/04/18.
 */

public interface IResturant {

    @GET(BuildConfig.CUSTOMER_URI)
    Observable<List<Customer>> getCustomers();

    @GET(BuildConfig.TABLE_URI)
    Observable<List<Boolean>> getTables();

/*
    @GET("movie/top_rated")
    Observable<ResultList> getTopRated(@Query("api_key") String api_key, @Query("page") int page);

    @GET("movie/{id}")
    Observable<Results> getMovie(@Path("id") int id, @Query("api_key") String api_key);

    @GET("search/movie")
    Observable<ResultList> getSearch(@Query("api_key") String api_key, @Query("query") String query, @Query("page") int page);*/

}
