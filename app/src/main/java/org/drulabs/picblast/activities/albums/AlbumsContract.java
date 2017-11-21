package org.drulabs.picblast.activities.albums;

import android.os.Bundle;

import org.drulabs.picblast.activities.BasePresenter;
import org.drulabs.picblast.activities.BaseView;
import org.drulabs.picblast.data.models.PixyAlbum;
import org.drulabs.picblast.data.models.PixyStatsHolder;

import java.util.HashMap;
import java.util.List;

/**
 * Created by kaushald on 15/11/17.
 */

public interface AlbumsContract {

    interface View extends BaseView<Presenter> {

        void onAlbumsFetched(List<PixyAlbum> albums);

        void onAlbumUpdated(PixyAlbum album);

        void showLoading(String message);

        void clearList();

        void updateStats(PixyStatsHolder statsHolder);

        void hideLoading();

        void navigateToAlbumDetails(Bundle albumInfo);

    }

    interface Presenter extends BasePresenter{

        void fetchAlbumList(String provider, String username, int page, int albumsPerPage,
                            HashMap<String, String> extras);

        void onAlbumClicked(PixyAlbum album);

        void filterAlbums(String searchText);

    }

}
