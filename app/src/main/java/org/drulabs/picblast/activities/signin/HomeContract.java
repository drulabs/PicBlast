package org.drulabs.picblast.activities.signin;

import android.os.Bundle;

import org.drulabs.picblast.activities.BasePresenter;
import org.drulabs.picblast.activities.BaseView;
import org.drulabs.picblast.data.models.ImgurAlbum;

import java.util.List;

/**
 * Created by kaushald.
 * TODO: What is this class / interface / enum is all about
 */

public interface HomeContract {

    interface Presenter extends BasePresenter {

        void fetchAlbumsForUser(String username);

        void handleLoginResult(Bundle extras);

    }

    interface View extends BaseView<Presenter> {

        void showResult(List<ImgurAlbum> albums);

        void showMessage(String message, boolean isError);

        void showLoading();

        void hideLoading();

    }

}
