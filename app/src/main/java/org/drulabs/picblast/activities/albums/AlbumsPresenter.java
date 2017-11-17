package org.drulabs.picblast.activities.albums;

import android.os.Bundle;

import org.drulabs.picblast.data.DataHandler;
import org.drulabs.picblast.data.models.ImgurAlbum;
import org.drulabs.picblast.data.models.PixyAlbum;
import org.drulabs.picblast.data.network.ImgurApi;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by kaushald on 16/11/17.
 */

public class AlbumsPresenter implements AlbumsContract.Presenter {

    private AlbumsContract.View mView;
    private DataHandler mDataHandler;

    @Inject
    AlbumsPresenter(AlbumsContract.View view, DataHandler dataHandler){
        this.mView = view;
        this.mDataHandler = dataHandler;
    }

    @Override
    public void start(Bundle extras) {
        // something that needs to run on start
    }

    @Override
    public void fetchAlbumList(String provider, String username, int page, int albumsPerPage,
                               HashMap<String, String> extras) {

        mDataHandler.fetchAlbums(false, new DataHandler.Callback<List<ImgurAlbum>>() {
            @Override
            public void onResult(List<ImgurAlbum> result) {

            }

            @Override
            public void onError(int code, String message) {

            }
        });


    }

    @Override
    public void onAlbumClicked(PixyAlbum album) {

    }
}
