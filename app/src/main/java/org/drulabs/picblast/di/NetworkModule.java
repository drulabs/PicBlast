package org.drulabs.picblast.di;

import org.drulabs.picblast.data.network.ApiBuilder;
import org.drulabs.picblast.data.network.ImgurApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kaushald.
 * TODO: What is this class / interface / enum is all about
 */
@Module
public class NetworkModule {

    public static final String CLIENT_ID = "03a49260c93c54f";

    private String mBaseURL;

    public NetworkModule(String baseURL) {
        this.mBaseURL = baseURL;
    }

    @Provides
    @Singleton
    Converter.Factory providesFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    ImgurApi providesImgurApi(Converter.Factory converterFactory) {
        return new ApiBuilder(mBaseURL)
                .addConverterFactory(converterFactory)
                .build();
    }

    private OkHttpClient buildOkhttpClient() {
        // TODO Create and return okhttp client that intercepts all requests and adds header
        return null;
    }
}
