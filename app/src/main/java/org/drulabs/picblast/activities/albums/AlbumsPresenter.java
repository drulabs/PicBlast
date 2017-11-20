package org.drulabs.picblast.activities.albums;

import android.os.Bundle;

import org.drulabs.picblast.data.DataHandler;
import org.drulabs.picblast.data.models.PixyAlbum;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kaushald on 16/11/17.
 */

public class AlbumsPresenter implements AlbumsContract.Presenter {

    private AlbumsContract.View mView;
    private DataHandler mDataHandler;

    private Observable<List<PixyAlbum>> albumObservable;
    private Observable<PixyAlbum> searchObservable;
    private CompositeDisposable compositeDisposables;

    @Inject
    AlbumsPresenter(AlbumsContract.View view, DataHandler dataHandler) {
        this.mView = view;
        this.mDataHandler = dataHandler;
        this.compositeDisposables = new CompositeDisposable();
        albumObservable = mDataHandler.fetchAlbums();
    }

    @Override
    public void start(Bundle extras) {
        // something that needs to run on start
    }

    @Override
    public void fetchAlbumList(String provider, String username, int page, int albumsPerPage,
                               HashMap<String, String> extras) {

        mView.showLoading(null);
        compositeDisposables.add(albumObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .subscribe(pixyAlbums -> {
                    mView.onAlbumsFetched(pixyAlbums);
                    mView.hideLoading();
                })
        );
    }

    @Override
    public void filterAlbums(String searchText) {

        searchObservable = mDataHandler.fetchAlbums(searchText);

        Disposable d = searchObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .doOnComplete(() -> {
                    searchObservable.unsubscribeOn(Schedulers.io());
                })
                .doOnNext(pixyAlbum -> mView.onAlbumUpdated(pixyAlbum))
                .subscribe();
        compositeDisposables.add(d);

    }

    @Override
    public void destroy() {
        compositeDisposables.dispose();
    }

    @Override
    public void onAlbumClicked(PixyAlbum album) {
        Bundle albumInfo = new Bundle();
        albumInfo.putString("album_id", album.getId());
        albumInfo.putString("cover", album.getCover());
        albumInfo.putString("provider", album.getProvider());
        albumInfo.putString("title", album.getTitle());
        albumInfo.putString("description", album.getDescription());
        mView.navigateToAlbumDetails(albumInfo);
    }
}
