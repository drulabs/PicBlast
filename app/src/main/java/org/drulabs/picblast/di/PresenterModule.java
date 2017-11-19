package org.drulabs.picblast.di;

import org.drulabs.picblast.activities.albums.AlbumsContract;
import org.drulabs.picblast.activities.albums.AlbumsPresenter;
import org.drulabs.picblast.activities.imgur.AlbumDetailsContract;
import org.drulabs.picblast.activities.imgur.AlbumDetailsPresenter;
import org.drulabs.picblast.activities.servicelogin.LoginContract;
import org.drulabs.picblast.activities.servicelogin.LoginPresenter;
import org.drulabs.picblast.activities.signin.HomeContract;
import org.drulabs.picblast.activities.signin.HomePresenter;
import org.drulabs.picblast.data.AppDataHandler;
import org.drulabs.picblast.data.DataHandler;

import dagger.Binds;
import dagger.Module;

/**
 * Created by kaushald.
 * TODO: What is this class / interface / enum is all about
 */
@Module
public abstract class PresenterModule {

    @Binds
    abstract HomeContract.Presenter bindHomePresenter(HomePresenter homePresenter);

    @Binds
    abstract LoginContract.Presenter bindLoginPresenter(LoginPresenter loginPresenter);

    @Binds
    abstract AlbumsContract.Presenter bindAlbumsPresenter(AlbumsPresenter albumsPresenter);

    @Binds
    abstract AlbumDetailsContract.Presenter bindAlbumDetailsPresenter(
            AlbumDetailsPresenter albumDetailsPresenter);

    @Binds
    abstract DataHandler bindsDataHandler(AppDataHandler appDataHandler);

}
