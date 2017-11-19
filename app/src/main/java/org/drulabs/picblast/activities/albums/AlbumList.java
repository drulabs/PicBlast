package org.drulabs.picblast.activities.albums;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import org.drulabs.picblast.R;
import org.drulabs.picblast.activities.imgur.AlbumDetails;
import org.drulabs.picblast.application.AppClass;
import org.drulabs.picblast.data.models.PixyAlbum;
import org.drulabs.picblast.di.DaggerViewComponent;
import org.drulabs.picblast.di.ViewModule;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by kaushald on 15/11/17.
 */

public class AlbumList extends AppCompatActivity implements AlbumsContract.View, AlbumsAdapter
        .AlbumClickListener {

    private RecyclerView rvAlbumList;
    private ProgressBar albumListLoader;
    private AlbumsAdapter albumsAdapter;

    @Inject
    AlbumsContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_list_layout);
        initializeUI();

        DaggerViewComponent.builder()
                .appComponent(((AppClass) getApplicationContext()).getAppComponent())
                .viewModule(new ViewModule(this))
                .build().inject(this);
    }

    private void initializeUI() {
        albumListLoader = findViewById(R.id.album_list_loader);
        rvAlbumList = findViewById(R.id.rv_albums);
        rvAlbumList.setLayoutManager(new LinearLayoutManager(AlbumList.this));
        albumsAdapter = new AlbumsAdapter(this);
        rvAlbumList.setAdapter(albumsAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.fetchAlbumList("imgur", "me", 1, 20, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(AlbumsContract.Presenter presenter) {

    }

    @Override
    public void onAlbumsFetched(List<PixyAlbum> albums) {
        albumsAdapter.append(albums);
    }

    @Override
    public void onAlbumUpdated(PixyAlbum album) {

    }

    @Override
    public void showLoading(String message) {
        rvAlbumList.setVisibility(View.GONE);
        albumListLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        rvAlbumList.setVisibility(View.VISIBLE);
        albumListLoader.setVisibility(View.GONE);
    }

    @Override
    public void navigateToAlbumDetails(Bundle albumInfo) {
        Intent albumDetailsIntent = new Intent(this, AlbumDetails.class);
        albumDetailsIntent.putExtras(albumInfo);
        startActivity(albumDetailsIntent);
    }

    @Override
    public void onAlbumClicked(PixyAlbum pixyAlbum) {
        mPresenter.onAlbumClicked(pixyAlbum);
    }
}
