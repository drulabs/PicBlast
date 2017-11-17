package org.drulabs.picblast.activities.albums;

import org.drulabs.picblast.activities.BasePresenter;
import org.drulabs.picblast.activities.BaseView;
import org.drulabs.picblast.data.models.PixyAlbum;

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

        void hideLoading();

        void closeApp();

    }

    interface Presenter extends BasePresenter{

        void fetchAlbumList(String provider, String username, int page, int albumsPerPage,
                            HashMap<String, String> extras);

        void onAlbumClicked(PixyAlbum album);

    }

}
