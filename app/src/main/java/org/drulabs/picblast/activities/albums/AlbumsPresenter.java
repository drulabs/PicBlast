package org.drulabs.picblast.activities.albums;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import org.drulabs.picblast.data.DataHandler;
import org.drulabs.picblast.data.models.PixyAlbum;
import org.drulabs.picblast.data.models.PixyStatsHolder;

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

    private Observable<List<PixyAlbum>> albumObservable;
    private Observable<PixyAlbum> searchObservable;
    private CompositeDisposable compositeDisposables;

    @Inject
    AlbumsPresenter(AlbumsContract.View view, DataHandler dataHandler) {
        this.mView = view;
        DataHandler mDataHandler = dataHandler;
        this.compositeDisposables = new CompositeDisposable();
        this.albumObservable = mDataHandler.fetchAlbums();
        this.searchObservable = mDataHandler.fetchAlbums("");
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
                .doOnError(Throwable::printStackTrace)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(pixyAlbums -> {
                    mView.onAlbumsFetched(pixyAlbums);
                    mView.hideLoading();
                })
                .subscribe()
        );

        compositeDisposables.add(
                searchObservable.subscribeOn(Schedulers.io())
                        .doOnError(Throwable::printStackTrace)
                        .doOnComplete(() -> searchObservable.unsubscribeOn(Schedulers.io()))
                        .map(pixyAlbum -> {
                            PixyStatsHolder statsHolder = new PixyStatsHolder();
                            statsHolder.setAlbumId(pixyAlbum.getId());
                            statsHolder.setImageCount(pixyAlbum.getImagesCount());
                            statsHolder.setViews(pixyAlbum.getViews());
                            return statsHolder;
                        })
                        .reduce((stats1, stats2) -> {
                            PixyStatsHolder statsHolder = new PixyStatsHolder();
                            statsHolder.setAlbumId(stats1.getAlbumId());
                            statsHolder.setViews(stats1.getViews() + stats2.getViews());
                            statsHolder.setImageCount(stats1.getImageCount() +
                                    stats2.getImageCount());
                            return statsHolder;
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(stats -> mView.updateStats(stats))
        );
    }

    @Override
    public void filterAlbums(String searchText) {

        Disposable d = searchObservable.subscribeOn(Schedulers.io())
                .doOnError(Throwable::printStackTrace)
                .doOnComplete(() -> searchObservable.unsubscribeOn(Schedulers.io()))
                .filter(pixyAlbum -> {
                    Log.d("FilterAlbum", "Filtering: main thread: " + (Looper.myLooper() ==
                            Looper.getMainLooper()));
                    return pixyAlbum.getTitle().contains(searchText) ||
                            pixyAlbum.getDescription().contains(searchText) ||
                            pixyAlbum.getProvider().contains(searchText);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(pixyAlbum -> {
                    mView.onAlbumUpdated(pixyAlbum);
                    Log.d("FilterAlbum", "UpdateAlbum: main thread: " + (Looper.myLooper() ==
                            Looper.getMainLooper()));
                })
                .observeOn(Schedulers.computation())
                .map(pixyAlbum -> {

                    Log.d("FilterAlbum", "Mapping: main thread: " + (Looper.myLooper() ==
                            Looper.getMainLooper()));

                    PixyStatsHolder statsHolder = new PixyStatsHolder();
                    statsHolder.setAlbumId(pixyAlbum.getId());
                    statsHolder.setImageCount(pixyAlbum.getImagesCount());
                    statsHolder.setViews(pixyAlbum.getViews());
                    return statsHolder;
                }).reduce((stats1, stats2) -> {
                    PixyStatsHolder statsHolder = new PixyStatsHolder();
                    statsHolder.setAlbumId(stats1.getAlbumId());
                    statsHolder.setViews(stats1.getViews() + stats2.getViews());
                    statsHolder.setImageCount(stats1.getImageCount() +
                            stats2.getImageCount());

                    Log.d("FilterAlbum", "Reducing: main thread: " + (Looper.myLooper() ==
                            Looper.getMainLooper()));

                    return statsHolder;
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(stats -> {
                    Log.d("FilterAlbum", "UpdateStats: main thread: " + (Looper.myLooper() ==
                            Looper.getMainLooper()));
                    mView.updateStats(stats);
                });
        compositeDisposables.add(d);

    }

    @Override
    public void destroy() {
        compositeDisposables.dispose();
        mView = null;
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
