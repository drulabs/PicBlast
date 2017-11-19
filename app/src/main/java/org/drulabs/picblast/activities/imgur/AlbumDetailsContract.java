package org.drulabs.picblast.activities.imgur;

import org.drulabs.picblast.activities.BasePresenter;
import org.drulabs.picblast.activities.BaseView;
import org.drulabs.picblast.data.models.PixyPic;

import java.util.List;

/**
 * Created by kaushald on 19/11/17.
 */

public interface AlbumDetailsContract {

    interface View extends BaseView<Presenter> {

        void setAlbumInfo(String albumName, String albumDesc, String cover);

        void onAlbumImagesFetched(List<PixyPic> pics);

        void showLoading();

        void hideLoading();

        void refresh();

        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {

        void onImageClicked(PixyPic pic);

        void fetchAlbumDetails();

        void uploadImage(String filePath);

    }

}
