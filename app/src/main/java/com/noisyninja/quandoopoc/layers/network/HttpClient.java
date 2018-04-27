package com.noisyninja.quandoopoc.layers.network;

import com.noisyninja.quandoopoc.BuildConfig;

import dagger.Module;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sudiptadutta on 27/04/18.
 */


@Module
public class HttpClient {

    private static Retrofit retrofit = null;

    public Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            String baseUrl = BuildConfig.BASE_URL;

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    /**
     * to bypass certificate errors, //todo not for production
     *
     * @return OkHttpClient.Builder with a empty trust manager
     * @throws RuntimeException
    private OkHttpClient.Builder getUnsafeOkHttpClient() throws RuntimeException {
    try {
    final TrustManager[] trustAllCerts = new TrustManager[]{
    new X509TrustManager() {
    @Override public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override public java.security.cert.X509Certificate[] getAcceptedIssuers() {
    return new java.security.cert.X509Certificate[]{};
    }
    }
    };

    // Install the all-trusting trust manager
    final SSLContext sslContext = SSLContext.getInstance("SSL");
    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
    // Create an ssl socket factory with our all-trusting manager
    final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
    builder.hostnameVerifier(new HostnameVerifier() {
    @Override public boolean verify(String hostname, SSLSession session) {
    return true;
    }
    });

    return builder;
    } catch (Exception e) {
    throw new RuntimeException(e);
    }
    }
     */
}
