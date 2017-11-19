package org.drulabs.picblast.data.network;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by kaushald.
 * TODO: What is this class / interface / enum is all about
 */

public class ImgurApiBuilder {

    private Retrofit.Builder serviceBuilder = null;
    private Retrofit retrofit = null;
    private Map<String, String> headerMap = null;

    public ImgurApiBuilder(String baseURL) {
        serviceBuilder = new Retrofit.Builder().baseUrl(baseURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        headerMap = new HashMap<>();
    }

    public ImgurApiBuilder addHeader(String key, String value) {
        headerMap.put(key, value);
        return this;
    }

    public ImgurApiBuilder addConverterFactory(Converter.Factory factory) {
        serviceBuilder.addConverterFactory(factory);
        return this;
    }

    @Nullable
    public ImgurApi build() {
        OkHttpClient okHttpClient = buildOkHttpClient();
        if (okHttpClient != null) {
            retrofit = serviceBuilder.client(okHttpClient).build();
            return retrofit.create(ImgurApi.class);
        }
        return null;
    }

    private OkHttpClient buildOkHttpClient() {
        try {

            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder.addInterceptor(chain -> {

                Request originalReq = chain.request();

                Request.Builder reqBuilder = originalReq.newBuilder();

                // Adding request headers
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    if (entry.getValue() != null) {
                        reqBuilder.header(entry.getKey(), entry.getValue());
                    }
                }

                reqBuilder.method(originalReq.method(), originalReq.body());
                Request newReq = reqBuilder.build();

                return chain.proceed(newReq);
            });
            return okHttpBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
