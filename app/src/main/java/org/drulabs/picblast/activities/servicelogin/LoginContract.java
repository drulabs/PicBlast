package org.drulabs.picblast.activities.servicelogin;

import android.content.Intent;

import org.drulabs.picblast.activities.BasePresenter;
import org.drulabs.picblast.activities.BaseView;

/**
 * Created by kaushald.
 * TODO: What is this class / interface / enum is all about
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void loadLoginView(String loginURL);

        void showLoading();

        void hideLoading();

        void setResultAndDismiss(Intent data);

        void showErrorAndDismiss(int errorStringResId);
    }

    interface Presenter extends BasePresenter {

        void handleLogin(Intent callbackIntent);

    }
}
