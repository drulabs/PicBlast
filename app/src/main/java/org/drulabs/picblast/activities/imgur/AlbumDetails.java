package org.drulabs.picblast.activities.imgur;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.drulabs.picblast.R;
import org.drulabs.picblast.application.AppClass;
import org.drulabs.picblast.data.models.PixyPic;
import org.drulabs.picblast.di.DaggerViewComponent;
import org.drulabs.picblast.di.ViewModule;
import org.drulabs.picblast.utils.Utility;

import java.util.List;

import javax.inject.Inject;

public class AlbumDetails extends AppCompatActivity implements AlbumDetailsContract.View, PicsAdapter.PicsListener {

    //Constants
    private static final int WRITE_PERMISSION_CODE = 90;
    private static final int GALLERY_CODE = 19;
    private final String WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String MIME_IMAGE = "image/*";

    // UI Elements
    private Toolbar toolbar;
    private TextView tvAlbumDesc;
    private ImageView imgCover;
    private RecyclerView rvAlbumPics;
    private ProgressBar albumPicsLoader;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    // Other variables
    @Inject
    AlbumDetailsContract.Presenter mPresenter;

    PicsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeUI();

        DaggerViewComponent.builder()
                .appComponent(((AppClass) getApplicationContext()).getAppComponent())
                .viewModule(new ViewModule(this))
                .build().inject(this);

        mPresenter.start(getIntent().getExtras());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    private void initializeUI() {
        tvAlbumDesc = findViewById(R.id.album_details_description);
        imgCover = findViewById(R.id.album_details_cover);
        rvAlbumPics = findViewById(R.id.rv_album_pics);
        albumPicsLoader = findViewById(R.id.album_pics_loader);
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        mAdapter = new PicsAdapter(this);
        rvAlbumPics.setLayoutManager(new LinearLayoutManager(this));
        rvAlbumPics.setAdapter(mAdapter);
    }

    public void onAddClicked(View view) {
        boolean isGranted = Utility.checkPermission(WRITE_PERMISSION, this);
        if (isGranted) {
            launchGallery();
        } else {
            Utility.requestPermission(WRITE_PERMISSION, WRITE_PERMISSION_CODE, this);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == WRITE_PERMISSION_CODE && permissions[0].equalsIgnoreCase
                (WRITE_PERMISSION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            launchGallery();
        } else {
            Toast.makeText(this, permissions[0] + " is required for app to " +
                    "function", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void refresh() {
        mPresenter.fetchAlbumDetails();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE:
                    if (data != null && data.getData() != null) {
                        Uri picUri = data.getData();
                        //String filePath = Utility.getFilePathFromURI(this, picUri);
                        String[] projection = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(picUri, projection, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(projection[0]);
                        String picturePath = cursor.getString(columnIndex);

                        Log.d("AlbumDetails", "onActivityResult: file: " + picturePath);
                        Log.d("AlbumDetails", "onActivityResult: file: " + picUri);
                        mPresenter.uploadImage(picturePath);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void launchGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType(MIME_IMAGE);
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, getString(R.string
                .pick_from_galery_title)), GALLERY_CODE);
    }

    @Override
    public void setPresenter(AlbumDetailsContract.Presenter presenter) {

    }

    @Override
    public void setAlbumInfo(String albumName, String albumDesc, String cover) {

        toolbar.setTitle(albumName);
        collapsingToolbarLayout.setTitle(albumName);
        tvAlbumDesc.setText(albumDesc);
        Picasso.with(this)
                .load(cover)
                .placeholder(R.mipmap.ic_launcher)
                .into(imgCover);

    }

    @Override
    public void onAlbumImagesFetched(List<PixyPic> pics) {
        mAdapter.append(pics);
    }

    @Override
    public void showLoading() {
        if (!isDestroyed()) {
            rvAlbumPics.setAlpha(0.3f);
            albumPicsLoader.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if (!isDestroyed()) {
            albumPicsLoader.setVisibility(View.GONE);
            rvAlbumPics.setAlpha(1.0f);
        }
    }

    @Override
    public void showMessage(String message) {
        if (!isDestroyed()) {
            Toast.makeText(AlbumDetails.this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPicClicked(PixyPic pic) {
        mPresenter.onImageClicked(pic);
    }
}
