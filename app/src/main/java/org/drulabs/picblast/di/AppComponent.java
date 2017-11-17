package org.drulabs.picblast.di;

import android.app.Application;
import android.content.Context;

import org.drulabs.picblast.data.PrefsStorage;
import org.drulabs.picblast.data.network.ImgurApi;
import org.drulabs.picblast.data.models.PixyAlbum;
import org.drulabs.picblast.data.models.PixyPic;

import javax.inject.Singleton;

import dagger.Component;
import io.objectbox.Box;

/**
 * Created by kaushald.
 * TODO: What is this class / interface / enum is all about
 */
@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

//    void inject(HomePresenter homePresenter);

    ImgurApi getImgurApi();

    Application getApplication();

    Box<PixyPic> getPixyPicBox();

    Box<PixyAlbum> getPixyAlbumBox();

    Context providesContext();

    PrefsStorage providesPrefsStorage();

}
