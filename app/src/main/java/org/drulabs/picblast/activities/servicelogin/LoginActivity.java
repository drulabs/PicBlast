package org.drulabs.picblast.activities.servicelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.drulabs.picblast.R;
import org.drulabs.picblast.application.AppClass;
import org.drulabs.picblast.di.DaggerViewComponent;
import org.drulabs.picblast.di.ViewModule;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @Inject
    LoginContract.Presenter mPresenter;

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DaggerViewComponent.builder()
                .appComponent(((AppClass) getApplicationContext()).getAppComponent())
                .viewModule(new ViewModule(this))
                .build().inject(this);
        webView = findViewById(R.id.login_view);
        mPresenter.start(null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.handleLogin(intent);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }

    @Override
    public void loadLoginView(final String loginURL) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                try {
                    if (request.getUrl().toString().startsWith("oauth-drulabs")) {
                        startActivity(new Intent(Intent.ACTION_VIEW, request.getUrl()));
                        return true;
                    }
                } catch (Exception exception) {
                }
                return false;
            }
        });
        webView.loadUrl(loginURL);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setResultAndDismiss(Intent data) {
        setResult(RESULT_OK, data);
        LoginActivity.this.finish();
    }

    @Override
    public void showErrorAndDismiss(int errorStringResId) {
        Toast.makeText(this, errorStringResId, Toast.LENGTH_SHORT).show();
        LoginActivity.this.finish();
    }
}
