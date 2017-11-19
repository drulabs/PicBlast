package org.drulabs.picblast.di;

import org.drulabs.picblast.activities.albums.AlbumList;
import org.drulabs.picblast.activities.albums.AlbumsContract;
import org.drulabs.picblast.activities.imgur.AlbumDetails;
import org.drulabs.picblast.activities.imgur.AlbumDetailsContract;
import org.drulabs.picblast.activities.servicelogin.LoginActivity;
import org.drulabs.picblast.activities.servicelogin.LoginContract;
import org.drulabs.picblast.activities.signin.HomeContract;
import org.drulabs.picblast.activities.signin.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kaushald.
 * TODO: What is this class / interface / enum is all about
 */
@Module
public class ViewModule {

    private MainActivity mainActivity;
    private LoginActivity loginActivity;
    private AlbumList albumList;
    private AlbumDetails albumDetails;

    public ViewModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public ViewModule(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public ViewModule(AlbumList albumList) {
        this.albumList = albumList;
    }

    public ViewModule(AlbumDetails albumDetails) {
        this.albumDetails = albumDetails;
    }

    @Provides
    @ActivityScope
    HomeContract.View providesHomeView() {
        return mainActivity;
    }

    @Provides
    @ActivityScope
    LoginContract.View providesLoginView() {
        return loginActivity;
    }

    @Provides
    @ActivityScope
    AlbumsContract.View providesAlbumList() {
        return albumList;
    }

    @Provides
    @ActivityScope
    AlbumDetailsContract.View providesAlbumDetails() {
        return albumDetails;
    }

//    @Provides
//    @ActivityScope
//    Context providesActivityContext() {
//        return mainActivity.getApplicationContext();
//    }

//    @Provides
//    HomeContract.Presenter bindHomePresenter(HomePresenter homePresenter) {
//        return homePresenter;
//    }
}
