package org.drulabs.picblast.activities.signin;

import android.os.Bundle;

import org.drulabs.picblast.data.DataHandler;
import org.drulabs.picblast.data.models.ImgurAlbum;
import org.drulabs.picblast.utils.Constants;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by kaushald.
 * TODO: What is this class / interface / enum is all about
 */
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;

    private DataHandler mDataHandler;

    @Inject
    public HomePresenter(HomeContract.View mView, DataHandler dataHandler) {
        this.mView = mView;
        this.mDataHandler = dataHandler;
        mView.setPresenter(this);
    }

    @Override
    public void start(Bundle extras) {
        boolean isLoggedIn = mDataHandler.getBoolean("is-logged-in");
        if (isLoggedIn) {
            mView.navigateToHomeAndKillSelf();
        }
    }

    @Override
    public void fetchAlbumsForUser(String username) {

        mView.showLoading();

//        mDataHandler.fetchAlbums(true, new DataHandler.Callback<List<ImgurAlbum>>() {
//            @Override
//            public void onResult(List<ImgurAlbum> result) {
//                mView.showResult(result);
//                mView.hideLoading();
//            }
//
//            @Override
//            public void onError(int code, String message) {
//                mView.showMessage(message, true);
//                mView.hideLoading();
//            }
//        });

    }

    @Override
    public void destroy() {
        this.mView = null;
    }

    @Override
    public void handleLoginResult(Bundle extras) {
        if (extras != null && extras.containsKey("provider")) {
            if (Constants.PROVIDER_IMGUR.equalsIgnoreCase(extras.getString("provider"))) {
                String token = extras.getString("access_token");
                String expiresIn = extras.getString("expires_in");
                mDataHandler.saveString("imgur-access-token", token);
                mDataHandler.saveString("token-expires-in", expiresIn);
                mDataHandler.saveBoolean("is-logged-in", true);
                mView.navigateToHomeAndKillSelf();
            }
        }
    }
}
