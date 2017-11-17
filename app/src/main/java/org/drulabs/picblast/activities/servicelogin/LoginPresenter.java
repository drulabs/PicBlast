package org.drulabs.picblast.activities.servicelogin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import org.drulabs.picblast.R;
import org.drulabs.picblast.di.NetworkModule;
import org.drulabs.picblast.data.network.ImgurApi;

import javax.inject.Inject;

/**
 * Created by kaushald.
 * TODO: What is this class / interface / enum is all about
 */

public class LoginPresenter implements LoginContract.Presenter {

    private static final String LOGIN_URL = "https://api.imgur.com/oauth2/authorize?client_id=" +
            NetworkModule.CLIENT_ID + "&response_type=token&state=drulabs";

    LoginContract.View mView;

    @Inject
    LoginPresenter(LoginContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start(Bundle extras) {
        mView.loadLoginView(LOGIN_URL);
    }

    @Override
    public void handleLogin(Intent callbackIntent) {
        Uri uri = callbackIntent.getData();
        if (uri != null && uri.toString().startsWith("oauth-drulabs")) {
            String error = uri.getQueryParameter("error");
            if (error == null || error.isEmpty()) {
                Uri newUri = Uri.parse(uri.toString().replaceAll("#", "&"));
                String oauthToken = newUri.getQueryParameter("access_token");
                String expiresIn = newUri.getQueryParameter("expires_in");
                Bundle data = new Bundle();
                data.putString("access_token", oauthToken);
                data.putString("expires_in", expiresIn);
                Intent dataIntent = new Intent();
                dataIntent.putExtras(data);
                mView.setResultAndDismiss(dataIntent);
            } else {
                mView.showErrorAndDismiss(R.string.imgur_access_denies);
            }
        }
    }
}
