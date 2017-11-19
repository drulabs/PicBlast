package org.drulabs.picblast.activities.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.drulabs.picblast.R;
import org.drulabs.picblast.activities.albums.AlbumList;
import org.drulabs.picblast.activities.servicelogin.LoginActivity;
import org.drulabs.picblast.application.AppClass;
import org.drulabs.picblast.di.DaggerViewComponent;
import org.drulabs.picblast.di.ViewModule;
import org.drulabs.picblast.data.models.ImgurAlbum;
import org.drulabs.picblast.utils.Constants;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements HomeContract.View {

    private static final int IMGUR_LOGIN_CODE = 22;

    @SuppressWarnings("unused")
    private static final int FLICKR_LOGIN_CODE = 33;

    @SuppressWarnings("unused")
    private static final int FACEBOOK_LOGIN_CODE = 44;

    EditText etUsername;

    @Inject
    HomeContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUsername = findViewById(R.id.et_username);

        DaggerViewComponent.builder()
                .appComponent(((AppClass) getApplicationContext()).getAppComponent())
                .viewModule(new ViewModule(this))
                .build().inject(this);
        mPresenter.start(getIntent().getExtras());
    }

    public void fetchAlbums(View view) {
        mPresenter.fetchAlbumsForUser(etUsername.getText().toString());
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {

    }

    @Override
    public void showResult(List<ImgurAlbum> albums) {
        //tvResult.setText(albums.toString());
    }

    @Override
    public void showMessage(String message, boolean isError) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
    }

    public void showImgurLogin(View view) {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(loginIntent, IMGUR_LOGIN_CODE);
    }

    @Override
    public void navigateToHomeAndKillSelf() {
        Intent landingIntent = new Intent(MainActivity.this, AlbumList.class);
        startActivity(landingIntent);
        MainActivity.this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMGUR_LOGIN_CODE:
                    Bundle bundleData = data.getExtras();
                    bundleData.putString("provider", Constants.PROVIDER_IMGUR);
                    String token = bundleData.getString("access_token");
                    String expiresIn = bundleData.getString("expires_in");
                    Log.d("MainActivity", "onActivityResult: " + token
                            + ", expires in : " + expiresIn);
                    mPresenter.handleLoginResult(bundleData);
                    break;
                default:
                    break;
            }
        }
    }


}
