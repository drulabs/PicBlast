package org.drulabs.picblast.di;

import org.drulabs.picblast.activities.albums.AlbumList;
import org.drulabs.picblast.activities.imgur.AlbumDetails;
import org.drulabs.picblast.activities.signin.MainActivity;
import org.drulabs.picblast.activities.servicelogin.LoginActivity;

import dagger.Component;

/**
 * Created by kaushald.
 * TODO: What is this class / interface / enum is all about
 */
@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {ViewModule.class, PresenterModule.class})
public interface ViewComponent {

    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

    void inject(AlbumList albumList);

    void inject(AlbumDetails albumDetails);
}
