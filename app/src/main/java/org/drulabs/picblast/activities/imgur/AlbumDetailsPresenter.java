package org.drulabs.picblast.activities.imgur;

import android.os.Bundle;

import org.drulabs.picblast.data.DataHandler;
import org.drulabs.picblast.data.models.ImgurResp;
import org.drulabs.picblast.data.models.PixyPic;
import org.drulabs.picblast.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kaushald on 19/11/17.
 */

public class AlbumDetailsPresenter implements AlbumDetailsContract.Presenter {

    DataHandler mDataHandler;
    AlbumDetailsContract.View mView;
    private Observable<List<PixyPic>> albumPicsObservable;
    private CompositeDisposable compositeDisposables;

    private String albumId;

    @Inject
    public AlbumDetailsPresenter(DataHandler dataHandler, AlbumDetailsContract.View view) {
        this.mDataHandler = dataHandler;
        this.mView = view;
        this.compositeDisposables = new CompositeDisposable();
    }

    @Override
    public void start(Bundle extras) {
        albumId = extras.getString("album_id");
        String albumCover = extras.getString("cover");
        String albumName = extras.getString("title");
        String albumDesc = extras.getString("description");
        mView.setAlbumInfo(albumName, albumDesc, albumCover);
        fetchAlbumDetails();
    }

    @Override
    public void destroy() {
        compositeDisposables.dispose();
    }

    @Override
    public void onImageClicked(PixyPic pic) {

    }

    @Override
    public void fetchAlbumDetails() {

        if (albumPicsObservable == null) {
            albumPicsObservable = mDataHandler.getAlbumImages(Constants.PROVIDER_IMGUR, albumId);
        }
        mView.showLoading();
        compositeDisposables.add(albumPicsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .subscribe(pixyPics -> {
                    mView.onAlbumImagesFetched(pixyPics);
                    mView.hideLoading();
                })
        );

    }

    @Override
    public void uploadImage(String filePath) {
        String title = String.valueOf((int) System.currentTimeMillis() / 1000);
        Single<ImgurResp> uploadSingle = mDataHandler.uploadImage(Constants.PROVIDER_IMGUR, albumId,
                filePath, title, "Uploaded from android app " + filePath.length(), title);
        mView.showLoading();
        uploadSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ImgurResp>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposables.add(d);
                    }

                    @Override
                    public void onSuccess(ImgurResp imgurResp) {
                        mView.showMessage("Upload successful in - album " + albumId);
                        mView.hideLoading();
                        mView.refresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.showMessage("Upload FAILURE");
                        mView.hideLoading();
                    }
                });
    }
}
