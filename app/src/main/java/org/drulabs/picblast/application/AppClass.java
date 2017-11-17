package org.drulabs.picblast.application;

import android.app.Application;

import com.facebook.stetho.Stetho;

import org.drulabs.picblast.data.models.MyObjectBox;
import org.drulabs.picblast.di.AppComponent;
import org.drulabs.picblast.di.AppModule;
import org.drulabs.picblast.di.DaggerAppComponent;
import org.drulabs.picblast.di.NetworkModule;

import io.objectbox.BoxStore;

/**
 * Created by kaushald.
 * TODO: What is this class / interface / enum is all about
 */
public class AppClass extends Application {

    private static final String BASE_URL = "https://api.imgur.com";

    protected AppComponent appComponent;

    private BoxStore boxStore;

    public AppComponent getComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(BASE_URL))
                .build();
        boxStore = MyObjectBox.builder().androidContext(this).build();
        Stetho.initializeWithDefaults(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
