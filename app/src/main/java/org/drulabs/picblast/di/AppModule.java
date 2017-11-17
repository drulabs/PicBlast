package org.drulabs.picblast.di;

import android.app.Application;
import android.content.Context;

import org.drulabs.picblast.application.AppClass;
import org.drulabs.picblast.data.PrefsStorage;
import org.drulabs.picblast.data.models.PixyAlbum;
import org.drulabs.picblast.data.models.PixyPic;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.objectbox.Box;

/**
 * Created by kaushald.
 * TODO: What is this class / interface / enum is all about
 */
@Module
public class AppModule {

    private final Application mApplication;

    public AppModule(Application app) {
        mApplication = app;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return mApplication.getApplicationContext();
    }


    @Provides
    @Singleton
    Box<PixyAlbum> providesPixyAlbumBox() {
        return ((AppClass) mApplication).getBoxStore().boxFor(PixyAlbum.class);
    }

    @Provides
    @Singleton
    Box<PixyPic> providesPixyPicBox() {
        return ((AppClass) mApplication).getBoxStore().boxFor(PixyPic.class);
    }

    @Provides
    @Singleton
    PrefsStorage providesPrefsStorage() {
        return PrefsStorage.getInstance(mApplication.getApplicationContext());
    }
}
